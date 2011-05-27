package de.saxsys.dojo.elevatorcontrol.impl;

import de.saxsys.dojo.elevatorcontrol.Direction;
import de.saxsys.dojo.elevatorcontrol.Elevator;
import de.saxsys.dojo.elevatorcontrol.ElevatorStatusVo;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marco.dierenfeldt
 */
public class ElevatorTestMockup extends Observable implements Elevator {

    private Direction direction = Direction.NONE;
    private int currentLevel = 0;

    public void moveTo(int level) {
        if (levelIsValid(level)) {
            direction = determinDirection(level);
            this.setChanged();
            this.notifyObservers(new ElevatorStatusVo(direction, currentLevel));
            
                startMoveTo(level);

            direction = Direction.NONE;
            this.setChanged();
            this.notifyObservers(new ElevatorStatusVo(direction, currentLevel));
        } else {
            throw new IllegalArgumentException("level " + level + " not in range (-2 ... +6)!");
        }
    }

    public void doEmergencyShutdown() {
        moveTo(0);
    }

    private boolean levelIsValid(int level) {
        boolean isValid = ((-2 <= level) && (level <= 6)) ? true : false;
        return isValid;
    }

    private void startMoveTo(int level)  {
        System.out.println("Elevator got order to move to "+level+ " dors are closing.");
        int incrementor = 0;
        switch (direction) {
            case DOWN: {
                incrementor = -1;
                break;
            }
            case UP: {
                incrementor = +1;
                break;
            }
        }

        if (incrementor != 0) {
            for (int i = currentLevel; i != level+incrementor; i += incrementor) {
               currentLevel = i;
                System.out.println("Elevator reached level " + i + " doors are closed.");
                this.setChanged();
                this.notifyObservers(new ElevatorStatusVo(direction, currentLevel));
            }
            System.out.println("Elevator arrived at level " + currentLevel + " dors are opene.");
        }
    }

    private Direction determinDirection(int level) {
        Direction result;
        if (level == currentLevel) {
            result = Direction.NONE;
        } else {
            result = (level > currentLevel) ? Direction.UP : Direction.DOWN;
        }

        return result;
    }
}
