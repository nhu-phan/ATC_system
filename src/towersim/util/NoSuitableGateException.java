package towersim.util;

/**
 * Exception thrown when there is no suitable gate available for an aircraft.
 */
public class NoSuitableGateException extends Exception {
    /** Constructs a NoSuitableGateException with no message */
    public NoSuitableGateException() {
        super();
    }

    /**
     * Constructs a NoSuitableGateException with a detailed message
     * @param message explaining why exception occurred
     */
    public NoSuitableGateException(String message) {
        super(message);
    }

}
