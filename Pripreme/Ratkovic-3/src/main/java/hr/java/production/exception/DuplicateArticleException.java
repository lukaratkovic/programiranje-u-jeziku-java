package hr.java.production.exception;

/**
 * Thrown when an attempt is made to add an Item to Store or Factory, but that Item is already selected for that Store of Factory
 */
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
