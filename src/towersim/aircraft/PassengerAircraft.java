package towersim.aircraft;

import org.w3c.dom.ls.LSOutput;
import towersim.tasks.Task;
import towersim.tasks.TaskList;
import towersim.tasks.TaskType;

/**
 * Represents an aircraft capable of carrying passenger cargo.
 */
public class PassengerAircraft extends Aircraft {
    /**
     * Average weight of single passenger including their baggage, in kilograms
     */
    public static final double AVG_PASSENGER_WEIGHT = 90.0;

    /**
     * Number of passengers on board
     **/
    private int numPassengers;

    /**
     * Constructs a new passenger aircraft
     * @param callsign represents unique callsign
     * @param characteristics including type, empty weight,
     *                        fuel/passenger/weight capacity
     * @param tasks represent a task list to be used by the aircraft
     * @param fuelAmount represents the current amount of fuel onboard
     * @param numPassengers represents the current amount of freight onboard in kilograms
     * @throws IllegalArgumentException if number of passengers is negative or
     * exceeds passenger capacity
     */
    public PassengerAircraft(String callsign, AircraftCharacteristics characteristics,
                             TaskList tasks, double fuelAmount, int numPassengers) {
        super(callsign, characteristics, tasks, fuelAmount);
        this.numPassengers = numPassengers;
        if ((numPassengers < 0) | (numPassengers > characteristics.passengerCapacity)) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Get the ratio of passengers on board to maximum passenger capacity as percentage.
     * @return Occupancy level as a percentage
     */
    public int calculateOccupancyLevel() {
        // in case of passengerCapacity == 0
        if (getCharacteristics().passengerCapacity == 0) {
            return 0;
        }
        double occupancyLevelDecimal = (double) (numPassengers * 100)
                / getCharacteristics().passengerCapacity;
        return (int) Math.round(occupancyLevelDecimal);
    }

    /**
     * Get the number of ticks required to load aircraft at the gate.
     * @return Number of ticks required to load aircraft at gate
     */
    public int getLoadingTime() {
        int loadPercent = this.getTaskList().getCurrentTask().getLoadPercent();
        double numPassengersToLoad = (double) (loadPercent)
                * getCharacteristics().passengerCapacity;
        double  loadingTimeDecimal = (double) Math.log10(numPassengersToLoad / 100);
        int loadingTime = (int) Math.round(loadingTimeDecimal);
        if (loadingTime == 0) {
            return 1;
        }
        return loadingTime;
    }

    /**
     * Get the total weight of aircraft in its current state (includes aircraft's empty weight,
     * fuel weight, passenger weight)
     * @return Total weight of aircraft in kilograms
     */
    @Override
    public double getTotalWeight() {
        return getCharacteristics().emptyWeight + getFuelAmount() * LITRE_OF_FUEL_WEIGHT
                + numPassengers * AVG_PASSENGER_WEIGHT;
    }

    /**
     * Updates aircraft's state on each tick of simulation.
     */
    @Override
    public void tick() {
        // call tick() in superclass  for refuelling
        super.tick();
        Task currentTask = getTaskList().getCurrentTask();
        int loadingTime = getLoadingTime();
        //calculate passenger to load for one tick
        if (currentTask.getType() == TaskType.LOAD) {
            double passengerToLoad = getCharacteristics().passengerCapacity
                    * (getTaskList().getCurrentTask().getLoadPercent());
            int passengerToLoadPerTick = (int) Math.round(passengerToLoad
                    / (this.getLoadingTime() * 100));
            this.numPassengers += passengerToLoadPerTick;
            if (numPassengers > getCharacteristics().passengerCapacity) {
                numPassengers = getCharacteristics().passengerCapacity;
            }
        }
    }
}
