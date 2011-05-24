/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.saxsys.dojo.elevatorcontrol.impl;

import de.saxsys.dojo.elevatorcontrol.Elevator;
import de.saxsys.dojo.elevatorcontrol.Direction;
import de.saxsys.dojo.elevatorcontrol.ElevatorLoggingObserver;
import de.saxsys.dojo.elevatorcontrol.ElevatorTestMockup;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marco.dierenfeldt
 */
public class ElevatorControllerImplTest {

    private Elevator nullElevator = null;
    private ElevatorTestMockup elevatorMock;
    private ElevatorLoggingObserver observer;

    public ElevatorControllerImplTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        elevatorMock = new ElevatorTestMockup();
        observer = new ElevatorLoggingObserver();
        elevatorMock.addObserver(observer);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addCall method, of class ElevatorControllerImpl.
     */
    @Test
    public void testAddCall() {
        System.out.println("addCall");
        int level = 0;
        ElevatorControllerImpl instance = new ElevatorControllerImpl(nullElevator);
        instance.addCall(level);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addAssignment method, of class ElevatorControllerImpl.
     */
    @Test
    public void testAddAssignment() {
        System.out.println("addAssignment");
        int level = 0;
        ElevatorControllerImpl instance = new ElevatorControllerImpl(nullElevator);
        instance.addAssignment(level);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getElevatorPosition method, of class ElevatorControllerImpl.
     */
    @Test
    public void testGetElevatorPositionAtStart() {
        System.out.println("getElevatorPosition");
        ElevatorControllerImpl instance = new ElevatorControllerImpl(nullElevator);
        int expResult = 0;
        int result = instance.getElevatorPosition();
        assertEquals(expResult, result);
    }

    /**
     * Test of getElevatorDirection method, of class ElevatorControllerImpl.
     */
    @Test
    public void testGetElevatorDirectionAtStart() {
        System.out.println("getElevatorDirection");
        ElevatorControllerImpl instance = new ElevatorControllerImpl(nullElevator);
        Direction expResult = Direction.NONE;
        Direction result = instance.getElevatorDirection();
        assertEquals(expResult, result);
    }

    /**
     * Test of doEmergencyShutdown method, of class ElevatorControllerImpl.
     */
    @Test
    public void testDoEmergencyShutdown() {
        System.out.println("doEmergencyShutdown");
        int level = 5;
        /*
         * first 0 because of status change of direction NONE -> UP
         * last five because of status change of direction UP -> NONE
         */
        int[] expectedLevels = {0, 0, 1, 2, 3, 4, 5, 5, 5, 5, 4, 3, 2, 1, 0, 0};
        Direction[] expectedDirections = {
            Direction.UP,
            Direction.UP,
            Direction.UP,
            Direction.UP,
            Direction.UP,
            Direction.UP,
            Direction.UP,
            Direction.NONE,
            Direction.DOWN,
            Direction.DOWN,
            Direction.DOWN,
            Direction.DOWN,
            Direction.DOWN,
            Direction.DOWN,
            Direction.DOWN,
            Direction.NONE};

        ElevatorControllerImpl instance = new ElevatorControllerImpl(elevatorMock);
        instance.addAssignment(5);
        instance.doEmergencyShutdown();

        int[] actualLevels = observer.getLevelsLogArray();
        assertArrayEquals(expectedLevels, actualLevels);

        Direction[] actualDirections = observer.getDirectionsLogArray();
        assertArrayEquals(expectedDirections, actualDirections);

    }

}