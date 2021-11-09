package hr.java.production.exception;

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
