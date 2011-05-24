package de.saxsys.dojo.elevatorcontrol;

/**
 * This value object is holding the status values of an elevator.
 * Its purpous is the transport of information vrom elevator to elevatorController
 *
 * @author marco.dierenfeldt
 */
public class ElevatorStatusVo {
    private Direction direction;
    private  int level;

    //generated constructor and accessors follow here

    public ElevatorStatusVo(Direction direction, int level) {
        this.direction = direction;
        this.level = level;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        StringBuilder sbu = new StringBuilder();
        sbu.append("Direction: ");
        sbu.append(direction);
        sbu.append("  level: ");
        sbu.append(level);

        return sbu.toString();
    }

    
}
