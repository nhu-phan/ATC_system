package towersim.util;

/**
 * Exception thrown when there is insufficient space to undertake an action
 */
public class NoSpaceException extends Exception {
    /** Constructs a NoSpaceException with no message */
    public NoSpaceException() {
        super();
    }

    /**
     * Constructs a NoSpaceException with a detailed message
     * @param message explaining why exception occurred
     */
    public NoSpaceException(String message) {
        super(message);
    }
}
