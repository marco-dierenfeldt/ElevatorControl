package de.saxsys.dojo.elevatorcontrol;

/**
 * The Elevator itself, it gets the commands fhere to move to
 * from the ElevatorController.
 * @author marco.dierenfeldt
 */
public interface Elevator {
    void moveTo(int level);
    void doEmergencyShutdown();
}
