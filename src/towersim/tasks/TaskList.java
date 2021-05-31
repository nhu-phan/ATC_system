package towersim.tasks;

import java.util.List;

/**
 * Represents a circular list of tasks for an aircraft to cycle through.
 */
public class TaskList {
    /** List of tasks */
    private List<Task> tasks;

    /** Current task in the list */
    private Task currentTask;

    /** Next task in the list */
    private Task nextTask;

    /**
     * Creates a new task list with given list of tasks
     * @param tasks list of tasks
     */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
        /* the first task should be the current task */
        this.currentTask = tasks.get(0);
    }

    /**
     * Get the current task.
     * @return the current task
     */
    public Task getCurrentTask() {
        return this.currentTask;
    }

    /**
     * Get the next task while remaining at the current task.
     * @return the next task
     */
    public Task getNextTask() {
        int indexOfCurrentTask = tasks.indexOf(getCurrentTask());
        /* if the current task is the last one in list, circle back to the first task */
        if (indexOfCurrentTask == (tasks.size() - 1)) {
            nextTask = tasks.get(0);
        } else {
            nextTask = tasks.get(indexOfCurrentTask + 1);
        }
        return nextTask;
    }

    /** Moves the current task forward by one in the circular task list */
    public void moveToNextTask() {
        this.currentTask = this.getNextTask();
    }

    /** Get the human-readable string representation of the task list
     * @return string representation of the task list
     */
    @Override
    public String toString() {
        return  "TaskList currently on " + getCurrentTask().getType() + " ["
                + (tasks.indexOf(getCurrentTask()) + 1) + "/" + tasks.size() + "]";
    }
}
