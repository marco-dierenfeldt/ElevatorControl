/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.saxsys.dojo.elevatorcontrol;

import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 *
 * @author marco.dierenfeldt
 */
public class ElevatorLoggingObserver implements Observer {

    private Queue<ElevatorStatusVo> statusQueue;
    private int[] levels;
    private Direction directions[];

    /**
     * Constructor in which the statusQueue is initialized.
     * Since this is a LoggingObserver for test purposes,
     * the size of the queue is limited to 1000.
     */
    public ElevatorLoggingObserver() {
        statusQueue = new ArrayBlockingQueue<ElevatorStatusVo>(1000);
    }

    /**
     * Implementation of the <code>Observer</code> interface.
     *
     * @param observable the Observable (here an Elevator) that calls this method.
     * @param statusObject this contains the current status values.
     */
    public void update(Observable observable, Object statusObject) {
        if (statusObject != null) {
            ElevatorStatusVo status = (ElevatorStatusVo) statusObject;
            statusQueue.add(status);
        }
    }

    /**
     * Accessor method for the statusQueue variable
     * @return the currrent statusQueue
     */
    public Queue<ElevatorStatusVo> getStatusQueue() {
        return statusQueue;
    }

    /**
     * Abstracted Accessor for the levelsLog array, which tests first, 
     * whether it is allready existent or calls the initializing method.
     * 
     * @return the levelsLog array
     */
    public int[] getLevelsLogArray() {
        if ((levels == null) && (directions == null)) {
            initLogArrays();
        }
        return levels;
    }

    /**
     * Abstracted Accessor for the directions array, which tests first,
     * whether it is allready existent or calls the initializing method.
     *
     * @return the directions array
     */
    public Direction[] getDirectionsLogArray() {
        if ((levels == null) && (directions == null)) {
            initLogArrays();
        }
        return directions;
    }

    /**
     * Initializing method for levelLogs and directions arrays.
     * It is called by the methods
     * <code>getDirectionsLogArray()</code>
     * and <code>getLevelsLogArray()</code> .
     */
    private void initLogArrays() {
        levels = new int[statusQueue.size()];
        directions = new Direction[statusQueue.size()];
        int i = 0;

        while (!statusQueue.isEmpty()) {
            ElevatorStatusVo status = statusQueue.poll();

            levels[i] = status.getLevel();
            directions[i] = status.getDirection();

            i++;
        }
    }
}
