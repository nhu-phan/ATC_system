package towersim.ground;

/**
 * Represents an airport terminal that is designed to accommodate helicopters.
 */
public class HelicopterTerminal extends Terminal {
    /** Terminal number */
    private int terminalNumber;

    /**
     * Creates a new helicopter terminal with a unique terminal number
     * @param terminalNumber unique number identifying the helicopter terminal
     */
    public HelicopterTerminal(int terminalNumber) {
        super(terminalNumber);
    }
}
