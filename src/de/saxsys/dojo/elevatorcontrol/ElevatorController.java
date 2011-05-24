package de.saxsys.dojo.elevatorcontrol;

import java.util.Observer;

/**
 * This interface is to be used in the coding dojo of the saxoniatag
 * in summer 2011.
 *
 * @author marco.dierenfeldt
 */
public interface ElevatorController{

    /**
     * Adds the call from on consumer with the according level the consumer
     * calls the elevator.
     *
     * @param level where the elevator is called.
     */
    void addCall(int level);

    /**
     * Adds one level to the Assignments of the elevator.
     *
     * @param level the level the elevator should go to
     */
    void addAssignment(int level);

    /**
     * Returns the current position of the elevator
     *
     * @return current position of the elevator
     */
    int getElevatorPosition();

     /**
     * In case of an emergency the elevator should move
     * to groundlevel immediately.
     */
    void doEmergencyShutdown();

   /**
     * Returns the current direction of the elevator (UP, DOWN, NONE)
     * 
     * @return the current direction of the elevator
     */
    Direction getElevatorDirection();
}
