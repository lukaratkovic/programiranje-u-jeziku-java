package hr.java.production.exception;

public class DuplicateArticleException extends Exception {
    public DuplicateArticleException(String message) {
        super(message);
    }

    public DuplicateArticleException(Throwable cause) {
        super(cause);
    }

    public DuplicateArticleException(String message, Throwable cause) {
        super(message, cause);
    }
}
