package towersim.ground;

import towersim.util.EmergencyState;
import towersim.util.NoSpaceException;
import towersim.util.NoSuitableGateException;
import towersim.util.OccupancyLevel;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an airport terminal building containing several aircraft gates.
 */
public abstract class Terminal implements EmergencyState, OccupancyLevel {
    /** Maximum possible number of gates allowed at a single terminal */
    public static final int MAX_NUM_GATES = 6;

    /** Unique terminal number that identifies the terminal */
    private int terminalNumber;

    /** Whether terminal is in state of emergency. True is in emergency state; false otherwise */
    private boolean isInEmergency;

    /** A list containing all the gates in the terminal */
    private List<Gate> gatesInTerminal;

    /** A list containing all the gates in the terminal (copy/duplicate version) */
    private List<Gate> copyOfGatesInTerminal;

    /**
     * Constructs a terminal object (where by default is not in a state of emergency)
     * @param terminalNumber represents a unique terminal number
     */
    protected Terminal(int terminalNumber) {
        this.terminalNumber = terminalNumber;
        gatesInTerminal = new ArrayList<>();
        isInEmergency = false;
    }

    /**
     * Get the terminal its unique terminal number.
     * @return the terminal number
     */
    public int getTerminalNumber() {
        return this.terminalNumber;
    }

    /**
     * Adds gate to the terminal
     * @param gate Gate object to be added to the terminal
     * @throws NoSpaceException if the number of gates in the terminal is already at maximum
     */
    public void addGate(Gate gate) throws NoSpaceException {
        if (gatesInTerminal.size() == MAX_NUM_GATES) {
            throw new NoSpaceException();
        } else {
            gatesInTerminal.add(gate);
        }
    }

    /**
     * Gets all the gates in the terminal object
     * @return all the gates in the terminal
     */
    public List<Gate> getGates() {
        copyOfGatesInTerminal = new ArrayList<>(gatesInTerminal);
        return copyOfGatesInTerminal;
    }

    /** Find the first unoccupied gate in the terminal.
     * @return first unoccupied gate in the terminal
     * @throws NoSuitableGateException if all gates are occupied
     */
    public Gate findUnoccupiedGate() throws NoSuitableGateException {
        for (int i = 0; i < gatesInTerminal.size(); i++) {
            if (gatesInTerminal.get(i).isOccupied() == false) {
                return gatesInTerminal.get(i);
            }
        }
        throw new NoSuitableGateException();
    }

    /**
     * Declares a state of emergency
     */
    public void declareEmergency() {
        isInEmergency = true;
    }

    /**
     * Clears any active state of emergency
     */
    public void clearEmergency() {
        isInEmergency = false;
    }

    /**
     * Finds out whether the terminal is in an active state of emergency
     * @return true if in emergency; false otherwise
     */
    public boolean hasEmergency() {
        return isInEmergency;
    }

    /**
     * Calculates the percentage of occupied gates
     * @return percentage of occupied gates in the terminal, from 0 to 100
     */
    public int calculateOccupancyLevel() {
        double numOccupiedGates = 0;
        for (int i = 0; i < gatesInTerminal.size(); i++) {
            if (gatesInTerminal.get(i).isOccupied() == true) {
                numOccupiedGates += 1;
            }
        }
        double occupancyRatio = (double) (numOccupiedGates * 100) / gatesInTerminal.size();
        return (int) Math.round(occupancyRatio);
    }

    /**
     * Get the human-readable representation of the gate.
     * @return string representation of the terminal object
     */
    @Override
    public String toString() {
        if (!isInEmergency) {
            return this.getClass().getSimpleName() + " " + terminalNumber + ", "
                    + getGates().size() + " gates";
        }
        return this.getClass().getSimpleName() + " " + terminalNumber + ", "
                + getGates().size() + " gates (EMERGENCY)";
    }
}
