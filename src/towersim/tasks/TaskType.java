package towersim.tasks;

/**
 * Represents the possible types of tasks an aircraft can have.
 */
public enum TaskType {
    /** Away means aircraft are either flying or at other airports */
    AWAY("Flying outside the airport"),

    /** Wait means aircraft is told to stay stationary at gate and not to load cargo*/
    WAIT("Waiting idle at gate"),

    /** Load means aircraft loading its cargo at gate */
    LOAD("Loading at gate"),

    /** Take-off means waiting on taxiways for a slot to take off */
    TAKEOFF("Waiting in queue to take off"),

    /** Land means aircraft are circling around the ariport waiting for a slot to land */
    LAND("Waiting in queue to land");

    /** Description explaining what each task type means */
    String description;

    TaskType(String description) {
        this.description = description;
    }

    /** Get the description of the task type/
     * @return description of task type */
    public String getDescription() {
        return description;
    }
}
