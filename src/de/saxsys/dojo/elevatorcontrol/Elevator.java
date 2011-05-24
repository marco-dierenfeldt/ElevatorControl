package de.saxsys.dojo.elevatorcontrol;

/**
 * The Elevator itself, it gets the commands where to move to
 * from the ElevatorController.
 *
 * @author marco.dierenfeldt
 */
public interface Elevator{

    /**
     * orders the elevator to go to the designated level
     *
     * @param level the level, the elevator should go to
     */
    void moveTo(int level);

    /**
     * In case of an emergency the elevator should move
     * to groundlevel immediately.
     */
    void doEmergencyShutdown();
    
}
