/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.saxsys.dojo.elevatorcontrol;

import de.saxsys.dojo.elevatorcontrol.impl.ElevatorTestMockup;
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
public class ElevatorTestMockupTest {

    ElevatorLoggingObserver observer;

    @Before
    public void setUp() {
        observer = new ElevatorLoggingObserver();
    }

    /**
     * Test of moveTo method, of class ElevatorTestMockup.
     */
    @Test
    public void testMoveToLevel5() {
        System.out.println("testMoveToLevel5");
        int level = 5;
        /*
         * first 0 because of status change of direction NONE -> UP
         * last five because of status change of direction UP -> NONE
         */
        int[] expectedLevels = {0, 0, 1, 2, 3, 4, 5, 5};
        Direction[] expectedDirections = {
            Direction.UP,
            Direction.UP,
            Direction.UP,
            Direction.UP,
            Direction.UP,
            Direction.UP,
            Direction.UP,
            Direction.NONE};

        ElevatorTestMockup instance = new ElevatorTestMockup();
        instance.addObserver(observer);
        instance.moveTo(level);

        int[] actualLevels = observer.getLevelsLogArray();
        assertArrayEquals(expectedLevels, actualLevels);

        Direction[] actualDirections = observer.getDirectionsLogArray();
        assertArrayEquals(expectedDirections, actualDirections);
    }

    /**
     * Test of doEmergencyShutdown method, of class ElevatorTestMockup.
     */
    @Test
    public void testDoEmergencyShutdownAtLevel5() {
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

        ElevatorTestMockup instance = new ElevatorTestMockup();
        instance.addObserver(observer);
        instance.moveTo(level);
        instance.doEmergencyShutdown();

        int[] actualLevels = observer.getLevelsLogArray();
        assertArrayEquals(expectedLevels, actualLevels);

        Direction[] actualDirections = observer.getDirectionsLogArray();
        assertArrayEquals(expectedDirections, actualDirections);
    }
}
