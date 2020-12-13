package com.guillaumecl.puzzle12;

public enum Instruction {
    NORTH,
    SOUTH,
    EAST,
    WEST,
    LEFT,
    RIGHT,
    FORWARD;

    public static Instruction fromChar(char c) {
        switch (c) {
            case 'N':
                return NORTH;
            case 'S':
                return SOUTH;
            case 'E':
                return EAST;
            case 'W':
                return WEST;
            case 'L':
                return LEFT;
            case 'R':
                return RIGHT;
            case 'F':
                return FORWARD;
            default:
                System.err.println("Unknown char: '" + c + "'");
                return FORWARD;
        }
    }
}
