package towersim.aircraft;

import towersim.tasks.Task;
import towersim.tasks.TaskList;
import towersim.tasks.TaskType;

/**
 * Represents an aircraft capable of carrying freight cargo.
 */
public class FreightAircraft extends Aircraft {
    /** Current amount of freight on board  in kilograohs*/
    private double freightAmount;

    /**
     * Constructs a new freight aircraft
     * @param callsign unique callsign
     * @param characteristics characteristics including type, empty weight,
     *                        fuel/passenger/weight capacity
     * @param tasks task list to be used
     * @param fuelAmount the current amount of fuel onboard
     * @param freightAmount the current amount of freight onboard in kilograms
     * @throws IllegalArgumentException if freight amount is less than zero or exceeds
     * freight capacity
     */
    public FreightAircraft(String callsign,
                           AircraftCharacteristics characteristics,
                           TaskList tasks,
                           double fuelAmount,
                           int freightAmount) {
        super(callsign, characteristics, tasks, fuelAmount);
        this.freightAmount = freightAmount;
        if ((freightAmount < 0) | (freightAmount > characteristics.freightCapacity)) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Get the total weight of the aircraft (empty weight plus fuel weight plus freight weight).
     * @return the total weight of aircraft in kilograms
     */
    public double getTotalWeight() {
        return getCharacteristics().emptyWeight + getFuelAmount() * LITRE_OF_FUEL_WEIGHT
                + this.freightAmount;
    }

    /**
     * Get the number of ticks required to load aircraft at the gate.
     * @return the number of ticks required to load the aircraft at the gate
     */
    public int getLoadingTime() {
        double freightToLoadDecimal = (double) getCharacteristics().freightCapacity
                * (getTaskList().getCurrentTask().getLoadPercent());
        freightToLoadDecimal = (double) freightToLoadDecimal / 100;
        int freightToLoad = (int) Math.round(freightToLoadDecimal);
        int ticks = 0;
        if (freightToLoad < 1000) {
            ticks = 1;
        } else if (freightToLoad < 50000) {
            ticks = 2;
        } else if (freightToLoad > 50000) {
            ticks = 3;
        }
        return ticks;
    }

    /**
     * Return the ratio of cargo onboard to maximum available freight capacity as percentage,
     * rounded to the nearest percentage point.
     * @return occupancy level as percentage */
    public int calculateOccupancyLevel() {
        //in case of when capacity == 0
        if (getCharacteristics().freightCapacity == 0) {
            return 0;
        }

        double occupancyLevelDecimal = (double) (freightAmount * 100)
                / getCharacteristics().freightCapacity;

        return (int) Math.round(occupancyLevelDecimal);
    }

    /**
     * Updates aircraft's state on each tick simulation
     * Refuel and update freightAmount based on freight to be loaded
     */
    public void tick() {
        // call tick() method in super for refueling/burning fuel
        super.tick();
        Task currentTask = getTaskList().getCurrentTask();
        double freightToLoadWithDecimal = getCharacteristics().freightCapacity
                * (getTaskList().getCurrentTask().getLoadPercent());
        double freightToLoad = Math.round(freightToLoadWithDecimal) / 100;
        if (currentTask.getType().equals(TaskType.LOAD)) {
            this.freightAmount += freightToLoad / this.getLoadingTime();
        }
    }

}


