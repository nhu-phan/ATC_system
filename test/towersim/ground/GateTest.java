package towersim.ground;

import org.junit.*;
import towersim.aircraft.AircraftCharacteristics;
import towersim.aircraft.FreightAircraft;
import towersim.aircraft.PassengerAircraft;
import towersim.tasks.Task;
import towersim.tasks.TaskList;
import towersim.tasks.TaskType;
import towersim.util.NoSpaceException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GateTest {
    private Gate gateOne;
    private Gate gateTwo;
    private Gate gateThree;
    private PassengerAircraft aircraftOne;
    private PassengerAircraft aircraftTwo;
    private PassengerAircraft helicopterOne;
    private FreightAircraft aircraftThree;
    private TaskList tasks;


    @Before
    public void setup() {
        // Creates gates
        gateOne = new Gate(1);
        gateTwo = new Gate(2);
        gateThree = new Gate(3);
        aircraftOne = new PassengerAircraft("ABC123", AircraftCharacteristics.AIRBUS_A320,tasks, 1234,
                67);
        aircraftTwo = new PassengerAircraft("DEF456", AircraftCharacteristics.FOKKER_100,tasks, 3423,
                54);
        helicopterOne = new PassengerAircraft("MNO789", AircraftCharacteristics.ROBINSON_R44,tasks, 56,
                0);
        aircraftThree = new FreightAircraft("XYZ209", AircraftCharacteristics.BOEING_747_8F,tasks, 3423,
                3433);
        }

    // Tests getGateNum()
    @Test
    public void getGateNumberTest() {
        assertEquals("Does not return correct gate number", 1, gateOne.getGateNumber());
        assertEquals("Does not return correct gate number", 2, gateTwo.getGateNumber());
        assertEquals("Does not return correct gate number", 3, gateThree.getGateNumber());
    }

    // Tests that the initially the aircraft is not occupied
    @Test
    public void constructorTestOne() {
        assertEquals("Gate should not be occupied initially", false,
                gateOne.isOccupied());
        assertEquals("Gate should not be occupied initially", false,
                gateTwo.isOccupied());
        assertEquals("Gate should not be occupied initially", false,
                gateThree.isOccupied());
    }

    // Tests that initially no aircraft is parked at gate
    @Test
    public void constructorTestTwo() {
        assertEquals("No aircraft should be parked at gate initially", null,
                gateOne.getAircraftAtGate());
        assertEquals("No aircraft should be occupied at the gate initially", null,
                gateTwo.getAircraftAtGate());
        assertEquals("No aircraft should be occupied at the gate initially", null,
                gateThree.getAircraftAtGate());
    }

    // Tests parkAircraft() where gate is not occupied
    @Test
    public void parkAircraftTestOne() throws NoSpaceException {
        gateOne.parkAircraft(aircraftOne);
        assertEquals(aircraftOne,gateOne.getAircraftAtGate());
        assertEquals(true, gateOne.isOccupied());
    }

    // Tests parkAircraft() where gate is occupied
    @Test
    public void parkAircraftTestTwo() throws NoSpaceException {
        gateOne.parkAircraft(aircraftOne);
        try {
            gateOne.parkAircraft(aircraftTwo);
            assert(false);
        }
        catch (NoSpaceException e) {
            // gateOne should be still occupied by aircraftOne;
            assertEquals(aircraftOne, gateOne.getAircraftAtGate());
            assertEquals(true, gateOne.isOccupied());
        }
    }

    // Tests isOccupied() is gate is occupied
    @Test
    public void isOccupiedTestOne() throws NoSpaceException {
        gateOne.parkAircraft(aircraftThree);
        gateThree.parkAircraft(helicopterOne);
        assertEquals("Should return true if gate is occupied", true, gateOne.isOccupied());
        assertEquals("Should return true if gate is occupied", true, gateThree.isOccupied());
    }

    // Tests isOccupied() is gate is unoccupied
    @Test
    public void isOccupiedTestTwo() throws NoSpaceException {
        assertEquals("Should return true if gate is occupied", false, gateOne.isOccupied());
        assertEquals("Should return true if gate is occupied", false, gateThree.isOccupied());
    }

    // Tests getAircraftAtGate() if gate is occupied
    @Test
    public void getAircraftAtGateTestOne() throws NoSpaceException {
        gateOne.parkAircraft(helicopterOne);
        gateThree.parkAircraft(aircraftThree);
        assertEquals("Should return Aircraft at gate if occupied", helicopterOne, gateOne.getAircraftAtGate());
        assertEquals("Should return Aircraft at gate if occupied", aircraftThree, gateThree.getAircraftAtGate());
    }

    // Tests getAircraftAtGate() if gate is unoccupied
    @Test
    public void getAircraftAtGateTestTwo() throws NoSpaceException {
        assertEquals("Should return null if gate is not occupied", null, gateOne.getAircraftAtGate());
        assertEquals("Should return null if gate is not occupied", null, gateTwo.getAircraftAtGate());
        assertEquals("Should return null if gate is not occupied", null, gateThree.getAircraftAtGate());
    }

    // Tests toString() if gate is occupied
    @Test
    public void toStringTestOne() throws NoSpaceException {
        gateOne.parkAircraft(aircraftOne);
        String expected = "Gate 1 [ABC123]";
        assertEquals("Does not return correct string representation", expected, gateOne.toString());
    }

    // Tests toString if gate is unoccupied
    @Test
    public void toStringTestTwo() throws NoSpaceException {
        String expected = "Gate 3 [empty]";
        assertEquals("Does not return correct string representation", expected, gateThree.toString());
    }

}
