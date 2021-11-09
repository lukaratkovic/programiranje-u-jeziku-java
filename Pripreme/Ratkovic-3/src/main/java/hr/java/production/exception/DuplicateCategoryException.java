package hr.java.production.exception;

/**
 * Thrown when an attempt is made to create a category with identical parameters as an existing category
 */
public class DuplicateCategoryException extends Exception {
    public DuplicateCategoryException(String message) {
        super(message);
    }

    public DuplicateCategoryException(Throwable cause) {
        super(cause);
    }

    public DuplicateCategoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
