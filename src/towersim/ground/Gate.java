package towersim.ground;

import towersim.aircraft.Aircraft;
import towersim.util.NoSpaceException;

/**
 * Represents an aircraft gate with facilities for a single aircraft to be parked.
 */
public class Gate {
    /** Unique gate number */
    private int gateNumber;

    /** Tells whether gate is occupied; true means occupied or false otherwise */
    private boolean hasBeenOccupied;

    /** Aircraft object parked at a specific gate */
    private Aircraft parkedAircraft;

    /**
     * Creates a new unoccupied gate
     * @param gateNumber a unique number identifying the gate
     */
    public Gate(int gateNumber) {
        this.hasBeenOccupied = false;
        parkedAircraft = null;
        this.gateNumber = gateNumber;
    }

    /**
     * Get the gate number.
     * @return the unique gate number
     * */
    public int getGateNumber() {
        return this.gateNumber;
    }

    /**
     * Park the aircraft object at the gate.
     * @param aircraft Aircraft to be parked at gate
     * @throws NoSpaceException if gate had already been occupied
     */
    public void parkAircraft(Aircraft aircraft) throws NoSpaceException {
        // if gate is occupied
        if (isOccupied()) {
            throw new NoSpaceException();
        } else {
            //park the aircraft at this gate so that gate becomes occupied
            hasBeenOccupied = true;
            parkedAircraft = aircraft;
        }
    }

    /** Removes currently parked aircraft from the gate */
    public void aircraftLeaves() {
        parkedAircraft = null;
        hasBeenOccupied = false;
    }

    /**
     * Checks whether an aircraft ic currently parked at the gate.
     * @return whether the gate is occupied; true if occupied or false otherwise
     */
    public boolean isOccupied() {
        return hasBeenOccupied;
    }

    /**
     * Get the aircraft object that is parked at the gate.
     * @return aircraft object that is currently being parked at the gate
     */
    public Aircraft getAircraftAtGate() {
        return parkedAircraft;
    }

    /**
     * Get the human-readable representation of the gate.
     * @return string representation of the gate object
     */
    public String toString() {
        if (parkedAircraft == null) {
            return "Gate " + gateNumber + " [empty]";
        }
        return "Gate " + gateNumber + " [" + getAircraftAtGate().getCallsign() + "]";
    }

}
