package towersim.ground;

import towersim.util.EmergencyState;

/**
 * Represents an airport terminal that is designed to accommodate airplanes
 */
public class AirplaneTerminal extends Terminal {
    /** A unique terminal number */
    private int terminalNumber;

    /**
     * Creates a new airplane terminal with a unique terminal number
     * @param terminalNumber unique  number identifying the airplane terminal
     */
    public AirplaneTerminal(int terminalNumber) {
        super(terminalNumber);
    }
}
