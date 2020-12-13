package com.guillaumecl.puzzle12;

public enum Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST;

    public static Direction rotateClockwiseBy90(Direction dir) {
        switch (dir) {
            case EAST:
                return SOUTH;
            case SOUTH:
                return WEST;
            case WEST:
                return NORTH;
            case NORTH:
                return EAST;
            default:
                System.err.println("WTF is this? " + dir);
                return EAST;
        }
    }

    public Direction rotateClockwiseBy(int deg) {
        if (deg % 90 != 0) {
            System.err.println("Cannot rotate by " + deg + ", not a multiple of 90.");
            return this;
        }
        while (deg >= 360) {
            deg -= 360;
        }
        while (deg < 0) {
            deg += 360;
        }
        Direction dir = this;
        while (deg > 0) {
            dir = rotateClockwiseBy90(dir);
            deg -= 90;
        }
        return dir;
    }
}
