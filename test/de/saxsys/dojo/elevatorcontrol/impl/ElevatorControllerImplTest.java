/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.saxsys.dojo.elevatorcontrol.impl;

import de.saxsys.dojo.elevatorcontrol.Elevator;
import de.saxsys.dojo.elevatorcontrol.Direction;
import de.saxsys.dojo.elevatorcontrol.ElevatorLoggingObserver;
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
    public void testAddCall() throws InterruptedException {
        System.out.println("addCall");
        int level = 6;
        /*
         * first 0 because of status change of direction NONE -> UP
         * last five because of status change of direction UP -> NONE
         */
        int[] expectedLevels = {0, 0, 1, 2, 3, 4, 5, 6, 6};
        Direction[] expectedDirections = {
            Direction.UP,
            Direction.UP,
            Direction.UP,
            Direction.UP,
            Direction.UP,
            Direction.UP,
            Direction.UP,
            Direction.UP,
            Direction.NONE};            

        ElevatorControllerImpl instance = new ElevatorControllerImpl(elevatorMock);
        instance.addCall(level);
        Thread.sleep(1000);
        int[] actualLevels = observer.getLevelsLogArray();
        assertArrayEquals(expectedLevels, actualLevels);

        Direction[] actualDirections = observer.getDirectionsLogArray();
        assertArrayEquals(expectedDirections, actualDirections);

    }

    /**
     * Test of addAssignment method, of class ElevatorControllerImpl.
     */
    @Test
    public void testAddAssignment() throws InterruptedException {
        System.out.println("addAssignment");
        int level = 4;
        /*
         * first 0 because of status change of direction NONE -> UP
         * last five because of status change of direction UP -> NONE
         */
        int[] expectedLevels = {0, 0, 1, 2, 3, 4, 4};
        Direction[] expectedDirections = {
            Direction.UP,
            Direction.UP,
            Direction.UP,
            Direction.UP,
            Direction.UP,
            Direction.UP,
            Direction.NONE};

        ElevatorControllerImpl instance = new ElevatorControllerImpl(elevatorMock);
        instance.addAssignment(level);
        
        Thread.sleep(1000);
        
        int[] actualLevels = observer.getLevelsLogArray();
        assertArrayEquals(expectedLevels, actualLevels);

        Direction[] actualDirections = observer.getDirectionsLogArray();
        assertArrayEquals(expectedDirections, actualDirections);
    }

    /**
     * Test of getElevatorPosition method, of class ElevatorControllerImpl.
     */
    @Test
    public void testGetElevatorPositionAtStart() {
        System.out.println("getElevatorPosition");
        ElevatorControllerImpl instance = new ElevatorControllerImpl(elevatorMock);
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
        ElevatorControllerImpl instance = new ElevatorControllerImpl(elevatorMock);
        Direction expResult = Direction.NONE;
        Direction result = instance.getElevatorDirection();
        assertEquals(expResult, result);
    }

    /**
     * Test of doEmergencyShutdown method, of class ElevatorControllerImpl.
     */
    @Test
    public void testDoEmergencyShutdown() throws InterruptedException {
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
        Thread.sleep(1000);
        instance.doEmergencyShutdown();

        int[] actualLevels = observer.getLevelsLogArray();
        assertArrayEquals(expectedLevels, actualLevels);

        Direction[] actualDirections = observer.getDirectionsLogArray();
        assertArrayEquals(expectedDirections, actualDirections);

    }

}