package towersim.tasks;

/**
 * Represents a task currently assigned to an aircraft. Tasks relate to an aircraft's
 * movement and ground operations.
 */
public class Task {
    /** Type of task, such as WAIT, LOAD, TAKEOFF, etc.  */
    private TaskType type;

    /** Percentage of maximum capacity to load */
    private int loadPercent = 0;

    /**
     * Creates a new Task of the given task type
     * @param type the type of task (such as WAIT, LAND, AWAY, etc.)
     * */
    public Task(TaskType type) {
        //constructor
        this.type = type;
    }

    /**
     * Creates a new Task of the given task type and stores the given load percentage in the task
     * @param type the type of task ; this constructor is used for LOAD tasks
     * @param loadPercent percentage of maximum capacity to load
     */
    public Task(TaskType type, int loadPercent) {
        this.type = type;
        this.loadPercent = loadPercent;
    }

    /**
     * Get the type of the task
     * @return task type
     */
    public TaskType getType() {
        return this.type;
    }

    /**
     * Get the load percentage specified when constructing the task.
     * @return the load percentage when constructing task, or 0 if none specified
     */
    public int getLoadPercent() {
        return loadPercent;
    }

    /**
     * Get the human-readable string representation of the task
     * @return string representation of the task
     */
    public String toString() {
        if (getType().equals(TaskType.LOAD)) {
            return getType() + " at " + this.getLoadPercent() + "%";
        }
        return getType().toString();
    }

}
