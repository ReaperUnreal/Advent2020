package com.guillaumecl.puzzle12.part1;

import com.guillaumecl.puzzle12.Direction;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public final class Boat {
    private int x;
    private int y;
    private Direction dir;

    public Boat() {
        x = 0;
        y = 0;
        dir = Direction.EAST;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getDir() {
        return dir;
    }

    public void applyStep(Step step) {
        int amount = step.getAmount();
        var instr = step.getInstr();
        switch (instr) {
            case EAST:
                x += amount;
                break;
            case WEST:
                x -= amount;
                break;
            case NORTH:
                y -= amount;
                break;
            case SOUTH:
                y += amount;
                break;
            case LEFT:
                dir = dir.rotateClockwiseBy(-amount);
                break;
            case RIGHT:
                dir = dir.rotateClockwiseBy(amount);
                break;
            case FORWARD:
                switch (dir) {
                    case EAST:
                        x += amount;
                        break;
                    case WEST:
                        x -= amount;
                        break;
                    case NORTH:
                        y -= amount;
                        break;
                    case SOUTH:
                        y += amount;
                        break;
                    default:
                        System.err.println("Unknown direction: " + dir);
                        break;
                }
                break;
            default:
                System.err.println("Unknown instruction: " + instr);
                break;
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("x", x)
                .append("y", y)
                .append("dir", dir)
                .toString();
    }
}
