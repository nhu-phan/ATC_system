package towersim.control;

import towersim.aircraft.Aircraft;
import towersim.aircraft.AircraftType;
import towersim.ground.Gate;
import towersim.ground.Terminal;
import towersim.tasks.TaskType;
import towersim.util.NoSpaceException;
import towersim.util.NoSuitableGateException;
import towersim.util.Tickable;
import java.util.ArrayList;
import java.util.List;

/** Represents a control tower at an airport */
public class ControlTower implements Tickable {
    /** List of terminals controlled by the tower */
    private final List<Terminal> controlledTerminals;

    /** List of terminals controlled by the tower (copied/duplicate version) */
    private List<Terminal> copyOfControlledTerminals;

    /** List of aircrafts controlled by the tower */
    private List<Aircraft> controlledAircrafts;

    /** List of aircrafts controlled by the tower (copied/duplicate version */
    private List<Aircraft> copyOfControlledAircrafts;

    /** Creates a new control tower with empty lists of controlled terminals and aircrafts */
    public ControlTower() {
        controlledTerminals = new ArrayList<>();
        controlledAircrafts = new ArrayList<>();
    }

    /**
     * Adds the given terminal to jurisdiction of control tower.
     * @param terminal to be added
     */
    public void addTerminal(Terminal terminal) {
        controlledTerminals.add(terminal);
    }

    /**
     * Get a list of all terminals managed by the control tower.
     * @return list of terminals managed by the tower
     */
    public List<Terminal> getTerminals() {
        copyOfControlledTerminals = new ArrayList<>(controlledTerminals);
        return copyOfControlledTerminals;
    }

    /**
     * Find an unoccupied gate in a compatible terminal for given aircraft
     * @param aircraft for which to find gate
     * @return gate for given aircraft
     * @throws NoSuitableGateException if no suitable gate found if current task type is
     * WAIT or LOAD
     */
    public Gate findUnoccupiedGate(Aircraft aircraft) throws NoSuitableGateException {
        /* This method first finds out the type of the aircraft passed in the argument,
        * whether it is a helicopter or airplane.
        * Then, it will loop through all the compatible terminals. For each terminal,
        * it will loop through the gates in the terminal and find the first unoccupied one.
        * If one terminal has no suitable gate, it will move to the next terminal.
        * If there is no unoccupied gate found after looping through all the terminals, it will
        * throw NoSuitableGateException */
        AircraftType type = aircraft.getCharacteristics().type;
        String terminalType;
        // if type is airplane
        if (type.equals(AircraftType.AIRPLANE)) {

            for (int i = 0; i < getTerminals().size(); i++) {
                terminalType = getTerminals().get(i).getClass().getSimpleName();
                try {
                    if (terminalType.equals("AirplaneTerminal")) {
                        return getTerminals().get(i).findUnoccupiedGate();
                    }
                } catch (NoSuitableGateException e) {
                    //pass to move onto new terminal
                }
            }
        } else {
            // if type is HELICOPTER
            for (int j = 0; j < getTerminals().size(); j++) {
                terminalType = getTerminals().get(j).getClass().getSimpleName();
                try {
                    if (terminalType.equals("HelicopterTerminal")) {
                        return getTerminals().get(j).findUnoccupiedGate();
                    }
                } catch (NoSuitableGateException f) {
                    //pass to move onto new terminal
                }
            }
        }
        /* If no suitable gate found */
        throw new NoSuitableGateException();
    }

    /**
     * Adds given aircraft to jurisdiction of control tower.
     * @param aircraft to be added.
     * @throws NoSuitableGateException if no suitable gate with current task type of WAIT or LOAD.
     */
    public void addAircraft(Aircraft aircraft) throws NoSuitableGateException {
        controlledAircrafts.add(aircraft);
        /* if TaskType == LOAD | WAIT, then park at suitable gate
        if no suitable gate, throw NoSuitableGateException */

        if (aircraft.getTaskList().getCurrentTask().getType().equals(TaskType.LOAD)
                | aircraft.getTaskList().getCurrentTask().getType().equals(TaskType.WAIT)) {
            try {
                findUnoccupiedGate(aircraft);
                // if there is a suitable gate, then park aircraft at such gate
                // if there is no suitable gate, then catch NoSuitableGateException
                findUnoccupiedGate(aircraft).parkAircraft(aircraft);
            } catch (NoSuitableGateException f) {
                throw new NoSuitableGateException();
            } catch (NoSpaceException e) {
                //
            }
        }
    }

    /**
     * Get a list of all the aircrafts under the control tower's jurisdiction.
     * @return a list of all aircrafts managed by the tower
     */
    public List<Aircraft> getAircraft() {
        copyOfControlledAircrafts = new ArrayList<>(controlledAircrafts);
        return copyOfControlledAircrafts;
    }

    /**
     * Find the gate where the given aircraft is parked
     * @param aircraft whose gate to find
     * @return gate occupied by aircraft; if not parked, return null
     */
    public Gate findGateOfAircraft(Aircraft aircraft) {
        Gate gateofAircraft = null;
        // loop through the terminals controlled by tower, if tower has no terminal, return null
        for (int i = 0; i < getTerminals().size(); i++) {
            if (getTerminals().size() == 0) {
                return gateofAircraft;
            } else {
                /* loop through each gate in each terminal, until found the gate occupied by
                said aircraft */
                List<Gate> gateList = getTerminals().get(i).getGates();
                for (int j = 0; j < gateList.size(); j++) {
                    // if no gates in terminal, return none;
                    if (gateList.size() == 0) {
                        // move to the next terminal because this terminal has no gate
                    } else {
                        if (gateList.get(j).getAircraftAtGate() == aircraft) {
                            gateofAircraft = gateList.get(j);
                        }
                    }
                }
            }
        }
        return gateofAircraft;
    }

    /** Advanced the simulation by tick */
    public void tick() {
        for (int i = 0; i < getAircraft().size(); i++) {
            getAircraft().get(i).tick();
        }
    }
}
