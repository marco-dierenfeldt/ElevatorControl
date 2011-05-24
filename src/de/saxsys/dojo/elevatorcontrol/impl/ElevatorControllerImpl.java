/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.saxsys.dojo.elevatorcontrol.impl;

import de.saxsys.dojo.elevatorcontrol.Direction;
import de.saxsys.dojo.elevatorcontrol.Elevator;
import de.saxsys.dojo.elevatorcontrol.ElevatorController;
import de.saxsys.dojo.elevatorcontrol.ElevatorStatusVo;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author marco.dierenfeldt
 */
public class ElevatorControllerImpl implements ElevatorController, Observer{

    private Elevator elevator;
    private Direction currentDirection;
    private int currentLevel;

    public ElevatorControllerImpl(Elevator elevator) {
        this.elevator = elevator;
        this.currentDirection = Direction.NONE;
        this.currentLevel = 0;
    }



    public void addCall(int level) {
        elevator.moveTo(level);
    }

    public void addAssignment(int level) {
        elevator.moveTo(level);
    }

    public int getElevatorPosition() {
        return currentLevel;
    }

    public Direction getElevatorDirection() {
        return currentDirection;
    }

    public void doEmergencyShutdown() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void update(Observable o, Object arg) {
        if (arg != null) {
            ElevatorStatusVo elevatorStatusVo = (ElevatorStatusVo) arg;
            this.currentDirection = elevatorStatusVo.getDirection();
            this.currentLevel = elevatorStatusVo.getLevel();
        }

    }
}
