-- Copyright 2004-2022 H2 Group. Multiple-Licensed under the MPL 2.0,
-- and the EPL 1.0 (https://h2database.com/html/license.html).
-- Initial Developer: H2 Group
--

CREATE TABLE TEST(C1 VARCHAR_IGNORECASE);
> ok

SELECT COLUMN_NAME, DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_NAME = 'TEST' ORDER BY ORDINAL_POSITION;
> COLUMN_NAME DATA_TYPE
> ----------- ------------------
> C1          VARCHAR_IGNORECASE
> rows (ordered): 1

DROP TABLE TEST;
> ok

CREATE TABLE TEST (N VARCHAR_IGNORECASE) AS VALUES 'A', 'a', NULL;
> ok

SELECT DISTINCT * FROM TEST;
> N
> ----
> A
> null
> rows: 2

SELECT * FROM TEST;
> N
> ----
> A
> a
> null
> rows: 3

DROP TABLE TEST;
> ok

CREATE TABLE TEST (N VARCHAR_IGNORECASE) AS VALUES 'A', 'a', 'C', NULL;
> ok

CREATE INDEX TEST_IDX ON TEST(N);
> ok

SELECT N FROM TEST WHERE N IN ('a', 'A', 'B');
> N
> -
> A
> a
> rows: 2

EXPLAIN SELECT N FROM TEST WHERE N IN (SELECT DISTINCT ON(B) A FROM VALUES ('a', 1), ('A', 2), ('B', 3) T(A, B));
>> SELECT "N" FROM "PUBLIC"."TEST" /* PUBLIC.TEST_IDX */ WHERE "N" IN( SELECT DISTINCT ON("B") "A" FROM (VALUES ('a', 1), ('A', 2), ('B', 3)) "T"("A", "B") /* table scan */)

SELECT N FROM TEST WHERE N IN (SELECT DISTINCT ON(B) A FROM VALUES ('a', 1), ('A', 2), ('B', 3) T(A, B));
> N
> -
> A
> a
> rows: 2

SELECT N FROM TEST WHERE N IN (SELECT DISTINCT ON(B) A FROM VALUES ('a'::VARCHAR_IGNORECASE, 1),
    ('A'::VARCHAR_IGNORECASE, 2), ('B'::VARCHAR_IGNORECASE, 3) T(A, B));
> N
> -
> A
> a
> rows: 2

EXPLAIN SELECT N FROM TEST WHERE N IN (SELECT DISTINCT ON(B) A FROM VALUES ('a'::VARCHAR_IGNORECASE(1), 1),
    ('A'::VARCHAR_IGNORECASE(1), 2), ('B'::VARCHAR_IGNORECASE(1), 3) T(A, B));
>> SELECT "N" FROM "PUBLIC"."TEST" /* PUBLIC.TEST_IDX: N IN(SELECT DISTINCT ON(B) A FROM (VALUES (CAST('a' AS VARCHAR_IGNORECASE(1)), 1), (CAST('A' AS VARCHAR_IGNORECASE(1)), 2), (CAST('B' AS VARCHAR_IGNORECASE(1)), 3)) T(A, B) /* table scan */) */ WHERE "N" IN( SELECT DISTINCT ON("B") "A" FROM (VALUES (CAST('a' AS VARCHAR_IGNORECASE(1)), 1), (CAST('A' AS VARCHAR_IGNORECASE(1)), 2), (CAST('B' AS VARCHAR_IGNORECASE(1)), 3)) "T"("A", "B") /* table scan */)

DROP INDEX TEST_IDX;
> ok

CREATE UNIQUE INDEX TEST_IDX ON TEST(N);
> exception DUPLICATE_KEY_1

DROP TABLE TEST;
> ok

CREATE MEMORY TABLE TEST(N VARCHAR_IGNORECASE) AS VALUES ('A'), ('a'), ('C'), (NULL);
> ok

CREATE HASH INDEX TEST_IDX ON TEST(N);
> ok

SELECT N FROM TEST WHERE N = 'A';
> N
> -
> A
> a
> rows: 2

DROP INDEX TEST_IDX;
> ok

CREATE UNIQUE HASH INDEX TEST_IDX ON TEST(N);
> exception DUPLICATE_KEY_1

DELETE FROM TEST WHERE N = 'A' LIMIT 1;
> update count: 1

CREATE UNIQUE HASH INDEX TEST_IDX ON TEST(N);
> ok

SELECT 1 FROM TEST WHERE N = 'A';
>> 1

INSERT INTO TEST VALUES (NULL);
> update count: 1

SELECT N FROM TEST WHERE N IS NULL;
> N
> ----
> null
> null
> rows: 2

DELETE FROM TEST WHERE N IS NULL LIMIT 1;
> update count: 1

SELECT N FROM TEST WHERE N IS NULL;
>> null

DROP TABLE TEST;
> ok

EXPLAIN VALUES CAST('a' AS VARCHAR_IGNORECASE(1));
>> VALUES (CAST('a' AS VARCHAR_IGNORECASE(1)))

CREATE TABLE T(C VARCHAR_IGNORECASE(0));
> exception INVALID_VALUE_2

CREATE TABLE T(C1 VARCHAR_IGNORECASE(1 CHARACTERS), C2 VARCHAR_IGNORECASE(1 OCTETS));
> ok

DROP TABLE T;
> ok

SELECT 'I' ILIKE CHAR(0x130);
>> TRUE

SET COLLATION TURKISH STRENGTH IDENTICAL;
> ok

CREATE TABLE TEST(V VARCHAR_IGNORECASE UNIQUE);
> ok

INSERT INTO TEST VALUES 'I', 'i';
> update count: 2

INSERT INTO TEST VALUES CHAR(0x0130);
> exception DUPLICATE_KEY_1

INSERT INTO TEST VALUES CHAR(0x0131);
> exception DUPLICATE_KEY_1

DROP TABLE TEST;
> ok

SET COLLATION OFF;
> ok


CREATE TABLE T1(A VARCHAR_IGNORECASE(1048576));
> ok

CREATE TABLE T2(A VARCHAR_IGNORECASE(1048577));
> exception INVALID_VALUE_PRECISION

SET TRUNCATE_LARGE_LENGTH TRUE;
> ok

CREATE TABLE T2(A VARCHAR_IGNORECASE(1048577));
> ok

SELECT TABLE_NAME, CHARACTER_MAXIMUM_LENGTH FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'PUBLIC';
> TABLE_NAME CHARACTER_MAXIMUM_LENGTH
> ---------- ------------------------
> T1         1048576
> T2         1048576
> rows: 2

SET TRUNCATE_LARGE_LENGTH FALSE;
> ok

DROP TABLE T1, T2;
> ok