package de.saxsys.dojo.elevatorcontrol;

/**
 * This interface is to be used in the coding dojo of the saxoniatag
 * in summer 2011.
 *
 * @author marco.dierenfeldt
 */
public interface ElevatorController {
    void addCall(int level);
    void addAssignment(int Level);
    int getElevatorPosition();
    Direction getElevatorDirection();
}
