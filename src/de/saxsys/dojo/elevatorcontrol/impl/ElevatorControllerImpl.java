/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.saxsys.dojo.elevatorcontrol.impl;

import de.saxsys.dojo.elevatorcontrol.Direction;
import de.saxsys.dojo.elevatorcontrol.Elevator;
import de.saxsys.dojo.elevatorcontrol.ElevatorController;
import de.saxsys.dojo.elevatorcontrol.ElevatorStatusVo;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marco.dierenfeldt
 */
public class ElevatorControllerImpl implements ElevatorController, Observer {

    private boolean emergencyShutdown = false;
    private Elevator elevator;
    private ElevatorStatusVo currentElevatorStatus;
    private Queue<Integer> assignmentQueue;
    private HashSet<Integer> assignmentHash;
    private ControllerWorkerThread worker;

    /**
     * Constructor of the controller.
     * Here the worker thread is startet.
     * 
     * @param elevator the elevator the controller controlls
     */
    public ElevatorControllerImpl(Elevator elevator) {
        this.elevator = elevator;

        this.currentElevatorStatus = new ElevatorStatusVo(Direction.NONE, 0);
        this.assignmentQueue = new ArrayBlockingQueue<Integer>(100);
        this.assignmentHash=new HashSet<Integer>();

        worker = new ControllerWorkerThread();
        worker.start();
    }

    /**
     * adds Call to the hashtable and rewrites the assignmentQueue
     * 
     * @param level the level to be added
     */
    public void addCall(int level) {
        if (!emergencyShutdown) {
            assignmentHash.add(level);
            assignmentQueue =
                    regenerateAssignementQueue(assignmentHash);
        }
    }

    /**
     * For this controller it is irrelevant, if the assignment is an 
     * assignment or a call, because the elevator behaves exactly the 
     * same for both. It stops at the assigned level and opens the door.
     * 
     * therefore, this implementation of the intrface redircts to the 
     * <code>addCall(int level)</code> method.
     * 
     * @param level the level to be added
     */
    public void addAssignment(int level) {
        addCall(level);
    }

    /**
     * Returns the elevator position which is updated by the
     * <code>Obeserver</code> method <code>update</code>
     * 
     * @return the currentLevel
     */
    public int getElevatorPosition() {
        return currentElevatorStatus.getLevel();
    }

    /**
     * Returns the elevator direction which is updated by the
     * <code>Obeserver</code> method <code>update</code>
     * 
     * @return the currentDirection
     */
    public Direction getElevatorDirection() {
        return currentElevatorStatus.getDirection();
    }

    /**
     * initiates the emergency shutdown -> elevator is moving to groundlevel 
     * and the <code>emergencyShutdown</code> variable 
     * is set to <code>true</code>
     */
    public void doEmergencyShutdown() {
        this.emergencyShutdown = true;
        this.elevator.doEmergencyShutdown();
        deleteAllRemainingAssignments();
    }

    /**
     * Implementation of the <code>update</code> method from the 
     * <code>Observer</code> interface. Hence we are only observing 
     * our elevator the <code>Object</code> parameter is allways an 
     * <code>ElevatorStatusVo</code>
     * 
     * @param observable the elevator
     * @param status an <code>ElevatorStatusVo</code> instance
     */
    public void update(Observable observable, Object status) {
        if (status != null) {
            ElevatorStatusVo elevatorStatusVo = (ElevatorStatusVo) status;
            this.currentElevatorStatus = elevatorStatusVo;
            int level = elevatorStatusVo.getLevel();
            if (assignmentHash.contains(level)) {
                assignmentHash.remove(level);
                this.assignmentQueue = regenerateAssignementQueue(assignmentHash);
            }
        }

    }

    private ArrayBlockingQueue<Integer> regenerateAssignementQueue(HashSet<Integer> hashSet) {
        return new ArrayBlockingQueue<Integer>(100, false, hashSet);
    }

    /**
     * This method is caled by the <code>doEmergencyShutdown</code> methos.
     * it empties the <code>assignmentQueue</code> and <code>assignmentHash</code>
     */
    private void deleteAllRemainingAssignments() {
        this.assignmentQueue.clear();
        this.assignmentHash.clear();
    }

    /**
     * Inner worker class, that does loop, while not emergencyShutdown = true.
     * It reads from the assignmentQueue and sends the move "command" to
     * the elevator. 
     */
    private class ControllerWorkerThread extends Thread {

        /**
         * Tthe overridden <code>run</code> method from the <code>Thread</code> class.
         */
        public void run() {
            while (!emergencyShutdown) {
                if ((!currentElevatorStatus.getDirection().equals(Direction.NONE))
                        || (assignmentQueue.size() == 0)) {
                    try {
                        this.sleep(500);
                    } catch (InterruptedException ex) {
                        //do nothing
                    }
                } else {
                    int destinationLevel = assignmentQueue.poll();
                    elevator.moveTo(destinationLevel);
                }
            }
        }
    }
}
