package towersim.tasks;

// add any required imports here

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TaskListTest {
    private TaskList tasks1;
    private TaskList tasks2;
    private TaskList tasks3;
    private List<Task> listOfTasks1;
    private List<Task> listOfTasks2;
    private List<Task> listOfTasks3;
    private Task away = new Task(TaskType.AWAY);
    private Task wait = new Task(TaskType.WAIT);
    private Task load = new Task(TaskType.LOAD, 65);
    private Task load2 = new Task(TaskType.LOAD, 0);
    private Task load3 = new Task(TaskType.LOAD, 100);
    private Task takeoff = new Task(TaskType.TAKEOFF);
    private Task land = new Task(TaskType.LAND);

    @Before
    public void setup() {
        // create first task list containing [AWAY, WAIT, LOAD]
        listOfTasks1 = new ArrayList<>();
        listOfTasks1.add(away);
        listOfTasks1.add(wait);
        listOfTasks1.add(load);
        tasks1 = new TaskList(listOfTasks1);

        // create second task list containing [LOAD]
        listOfTasks2 = new ArrayList<>();
        listOfTasks2. add(load2);
        tasks2 = new TaskList(listOfTasks2);

        // create third task list containing [TAKEOFF, AWAY, WAIT, LOAD, TAKEOFF]
        listOfTasks3 = new ArrayList<>();
        listOfTasks3.add(takeoff);
        listOfTasks3.add(away);
        listOfTasks3.add(wait);
        listOfTasks3.add(load3);
        listOfTasks3.add(takeoff);
        tasks3 = new TaskList(listOfTasks3);

    }

    // Tests getCurrentTask(), where list contains 3 tasks
    @Test
    public void getCurrentTaskTestOne() {
        assertEquals("Does not correctly return current task",away, tasks1.getCurrentTask());
    }

    // Tests getCurrentTask(), where list contains 1 task
    @Test
    public void getCurrentTaskTestTwo() {
        assertEquals("Does not correctly return current task", load2, tasks2.getCurrentTask());
    }

    // Tests getCurrentTask(), after moving to next task
    @Test
    public void getCurrentTaskTestThree() {
        tasks3.moveToNextTask();
        assertEquals("Does not correctly return current task", away, tasks3.getCurrentTask());
    }

    // Tests getCurrentTask(), after calling getNextTask()
    // because current task should remain the same
    @Test
    public void getCurrentTaskTestFour() {
        tasks1.getNextTask();
        assertEquals("Does not correctly return current task", away, tasks1.getCurrentTask());
    }

    // Tests getNextTask(), where list contains 1 task.
    // Current task should stay the same
    @Test
    public void getNextTaskTestOne() {
        assertEquals("Does not correctly return next task", load2, tasks2.getNextTask());
        assertEquals("Current task should stay the same",load2, tasks2.getCurrentTask());
    }

    // Tests getNextTask(), where list contains 3 tasks
    @Test
    public void getNextTaskTestTwo() {
        assertEquals("Does not correctly return next task", wait,tasks1.getNextTask());
        assertEquals("Current task should stay the same",away, tasks1.getCurrentTask());
    }

    // Tests getNextTask() if current task is the last one in the list; list should be circular
    // Current task should remain the same
    @Test
    public void getNextTaskTestThree() {
        Task expected = away;
        tasks1.moveToNextTask(); // on WAIT
        tasks1.moveToNextTask(); // on LOAD
        assertEquals("Does not correctly return next task",expected, tasks1.getNextTask());
        assertEquals("Current task should stay the same",load, tasks1.getCurrentTask());
    }

    // Tests moveToNextTask() if there is only one task; current task and next task should change
    @Test
    public void moveToNextTaskTestOne() {
        Task expected = load2;
        tasks2.moveToNextTask();
        assertEquals("Does not correctly return next task after moving tasks", expected, tasks2.getNextTask());
        assertEquals("Does not correct return current task after moving tasks", expected,
                tasks2.getCurrentTask());
    }

    // Tests moveToNextTask() if the current task is in the middle of the list
    @Test
    public void moveToNextTaskTestTwo() {
        tasks3.moveToNextTask(); // NOW AT AWAY
        tasks3.moveToNextTask(); // NOW AT WAIT
        Task expectedCurrentTask = wait;
        Task expectedNextTask = load3;
        assertEquals("Does not correctly return next task after moving tasks", expectedNextTask, tasks3.getNextTask());
        assertEquals("Does not correct return current task after moving tasks", expectedCurrentTask,
                tasks3.getCurrentTask());
    }

    // Tests moveToNextTask() if the current task last in the list
    @Test
    public void moveToNextTaskTestThree() {
        tasks1.moveToNextTask(); // NOW AT WAIT
        tasks1.moveToNextTask(); // NOW AT LOAD which is last one in task list
        Task expectedCurrentTask = load;
        Task expectedNextTask = away;
        assertEquals("Does not correctly return next task after moving tasks", expectedNextTask, tasks1.getNextTask());
        assertEquals("Does not correct return current task after moving tasks", expectedCurrentTask,
                tasks1.getCurrentTask());
    }

    // Tests toString() where task list only has one task
    @Test
    public void toStringTestOne() {
        String expected = "TaskList currently on LOAD [1/1]";
        assertEquals("Does not return correct string representation", expected, tasks2.toString());
    }

    // Tests toString() where currently on last task
    @Test
    public void toStringTestTwo() {
        tasks1.moveToNextTask(); // now on AWAY
        tasks1.moveToNextTask(); // now on LOAD
        String expected = "TaskList currently on LOAD [3/3]";
        assertEquals("Does not return correct string representation", expected, tasks1.toString());
    }

    // Tests toString() where currently on the task in the middle of list
    @Test
    public void toStringTestThree() {
        tasks3.moveToNextTask(); // now on AWAY
        tasks3.moveToNextTask(); // now on WAIT (3/5)
        String expected = "TaskList currently on WAIT [3/5]";
        assertEquals("Does not return correct string representation", expected, tasks3.toString());
    }
}
