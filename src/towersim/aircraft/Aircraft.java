package towersim.aircraft;

import towersim.tasks.Task;
import towersim.tasks.TaskList;
import towersim.tasks.TaskType;
import towersim.util.EmergencyState;
import towersim.util.OccupancyLevel;
import towersim.util.Tickable;

/**
 *An abstract class for aircraft whose movement is managed by the system.
 */

public abstract class Aircraft implements OccupancyLevel, Tickable, EmergencyState {
    /** Weight of a litre of aviation fuel, in kilograms */
    public static final double LITRE_OF_FUEL_WEIGHT = 0.8;

    /** The task list to be used by aircraft */
    private TaskList tasks;

    /** The unique callsign of the airplane */
    private String callsign;

    /** Characteristics that describe aircraft, such as type, empty weight,
     * fuel/passenger/weight capacity */
    private AircraftCharacteristics characteristics;

    /** Current fuel amount in Litres */
    private double fuelAmount;

    /** Whether the airplane is in emergency state. True if in emergency; otherwise false */
    private boolean inEmergencyState;

    /**
     * Constructs a new aircraft
     * @param callsign unique callsign
     * @param characteristics incl. type, empty weight, fuel/passenger/weight capacity
     * @param tasks task list to be used
     * @param fuelAmount the current amount of fuel onboard
     * @throws IllegalArgumentException if fuelAmount is negative or exceeds the fuel capacity
     */
    protected Aircraft(String callsign,
                       AircraftCharacteristics characteristics,
                       TaskList tasks,
                       double fuelAmount) {
        inEmergencyState = false;
        this.callsign = callsign;
        this.characteristics = characteristics;
        this.tasks = tasks;
        this.fuelAmount = fuelAmount;
        if ((fuelAmount < 0) | (fuelAmount > characteristics.fuelCapacity)) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Get the callsign of the aircraft.
     * @return the airplane's callsign
     */
    public String getCallsign() {
        return this.callsign;
    }

    /**
     * Get the current fuel amount of the aircraft.
     * @return the current fuel amount in litres
     */
    public double getFuelAmount() {
        return this.fuelAmount;
    }

    /**
     * Get the aircraft's characteristics (i.e. AIRBUS_A320)
     * @return aircraft's characteristics
     */
    public AircraftCharacteristics getCharacteristics() {
        return this.characteristics;
    }

    /**
     * Get the percentage of fuel remaining rounded to nearest integer.
     * @return the percentage of fuel remaining
     */
    public int getFuelPercentRemaining() {
        double percentRemaining = (getFuelAmount() / characteristics.fuelCapacity)
                * 100; //with decimals
        return (int) Math.round(percentRemaining);
    }

    /**
     * Get the total weight of the aircraft.
     * @return the total weight of aircraft in kilograms
     */
    public double getTotalWeight() {
        return this.characteristics.emptyWeight + getFuelAmount() * LITRE_OF_FUEL_WEIGHT;
    }

    /**
     * Get the task list of the aircraft.
     * @return the task list of the aircraft
     */
    public TaskList getTaskList() {
        return this.tasks;
    }

    /**
     * An abstract method to get the number ticks required to load the aircraft at gate.
     * @return the number of ticks required to load the aircraft
     */
    public abstract int getLoadingTime();

    /**
     * Updates aircraft's state on each tick of simulation
     */
    public void tick() {
        // The for step in tick, the answer is rounded to the nearest integer
        Task currentTask = tasks.getCurrentTask();
        if (currentTask.getType().equals(TaskType.AWAY)) {
            double fuelReduced = (double) characteristics.fuelCapacity * 0.1;
            fuelAmount -= Math.round(fuelReduced);
            if (fuelAmount < 0) {
                fuelAmount = 0;
            }
        } else if (currentTask.getType().equals(TaskType.LOAD)) {
            int loadingTime = this.getLoadingTime();
            double fuelLoaded = (double) characteristics.fuelCapacity / loadingTime;
            fuelAmount += Math.round(fuelLoaded);
        }
        // in case if refuelling results fuel onboard exceeding max fuel capacity
        if (fuelAmount > characteristics.fuelCapacity) {
            fuelAmount = characteristics.fuelCapacity;
        }
    }

    /**
     * Return a human-readable string representation of the aircraft.
     * @return string representation of the aircraft object
     */
    @Override
    public String toString() {
        if (inEmergencyState == true) {
            return characteristics.type + " " + callsign + " " + characteristics + " "
                    + tasks.getCurrentTask() + " (EMERGENCY)";
        }
        return characteristics.type + " " + callsign + " " + characteristics + " "
                + tasks.getCurrentTask();
    }

    /** Clears any active emergency
     */
    public void clearEmergency() {
        this.inEmergencyState = false;
    }

    /** Declares a state of emergency
     */
    public void declareEmergency() {
        this.inEmergencyState = true;
    }

    /** Tell whether or not there is an active state of emergency
     * @return true if there is an active state of emergency; false otherwise
     */
    public boolean hasEmergency() {
        return this.inEmergencyState;
    }

}
