package com.guillaumecl.puzzle11;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.log4j.lf5.viewer.TrackingAdjustmentListener;

import java.util.Arrays;
import java.util.List;

public final class Floor {
    private final byte[] arr;
    private final int width;
    private final int height;

    private final static char FLOOR_CHAR = '.';
    private final static char EMPTY_CHAR = 'L';
    private final static char TAKEN_CHAR = '#';
    private final static char UNKNOWN_CHAR = '?';

    private final static byte FLOOR = 0;
    private final static byte EMPTY = 1;
    private final static byte TAKEN = 2;
    private final static byte UNKNOWN = -1;

    private Floor(byte[] arr, int width, int height) {
        this.arr = arr;
        this.width = width;
        this.height = height;
    }

    private int countNeighborhood(int startIdx) {
        int count = 0;
        int startX = startIdx % width;
        int startY = startIdx / width;
        for (int yOffset = -1; yOffset <= 1; yOffset++) {
            int y = startY + yOffset;
            if (y < 0 || y >= height) {
                continue;
            }
            for (int xOffset = -1; xOffset <= 1; xOffset++) {
                int x = startX + xOffset;
                if (x < 0 || x >= width) {
                    continue;
                }
                if (xOffset == 0 && yOffset == 0) {
                    continue;
                }
                int idx = x + y * width;
                if (arr[idx] == TAKEN) {
                    count++;
                }
            }
        }
        return count;
    }

    public Floor advance() {
        int size = width * height;
        byte[] stepArr = new byte[size];
        for (int idx = 0; idx < size; idx++) {
            var val = arr[idx];
            switch (val) {
                case FLOOR:
                    stepArr[idx] = FLOOR;
                    break;
                case EMPTY:
                    if (countNeighborhood(idx) == 0) {
                        stepArr[idx] = TAKEN;
                    } else {
                        stepArr[idx] = EMPTY;
                    }
                    break;
                case TAKEN:
                    if (countNeighborhood(idx) >= 4) {
                        stepArr[idx] = EMPTY;
                    } else {
                        stepArr[idx] = TAKEN;
                    }
                default:
                    break;
            }
        }
        return new Floor(stepArr, width, height);
    }

    public int countNeighborhood2(int startIdx) {
        int count = 0;
        int startX = startIdx % width;
        int startY = startIdx / width;
        for (int yStep = -1; yStep <= 1; yStep++) {
            for (int xStep = -1; xStep <= 1; xStep++) {
                if (xStep == 0 && yStep == 0) {
                    continue;
                }
                int y = startY + yStep;
                int x = startX + xStep;
                while (x >= 0 && x < width && y >= 0 && y < height) {
                    var val = arr[x + y * width];
                    if (val == TAKEN) {
                        count++;
                        break;
                    } else if (val == EMPTY) {
                        break;
                    }
                    x += xStep;
                    y += yStep;
                }
            }
        }
        return count;
    }

    public Floor advance2() {
        int size = width * height;
        byte[] stepArr = new byte[size];
        for (int idx = 0; idx < size; idx++) {
            var val = arr[idx];
            switch (val) {
                case FLOOR:
                    stepArr[idx] = FLOOR;
                    break;
                case EMPTY:
                    if (countNeighborhood2(idx) == 0) {
                        stepArr[idx] = TAKEN;
                    } else {
                        stepArr[idx] = EMPTY;
                    }
                    break;
                case TAKEN:
                    if (countNeighborhood2(idx) >= 5) {
                        stepArr[idx] = EMPTY;
                    } else {
                        stepArr[idx] = TAKEN;
                    }
                default:
                    break;
            }
        }
        return new Floor(stepArr, width, height);
    }

    public int getNumTaken() {
        int count = 0;
        for (int idx = 0; idx < arr.length; idx++) {
            if (arr[idx] == TAKEN) {
                count++;
            }
        }
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Floor floor = (Floor) o;

        return new EqualsBuilder()
                .append(width, floor.width)
                .append(height, floor.height)
                .append(arr, floor.arr)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(arr)
                .append(width)
                .append(height)
                .toHashCode();
    }

    @Override
    public String toString() {
        String str = "";
        int idx = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++, idx++) {
                byte val = arr[idx];
                char c;
                switch(val) {
                    case FLOOR:
                        c = FLOOR_CHAR;
                        break;
                    case EMPTY:
                        c = EMPTY_CHAR;
                        break;
                    case TAKEN:
                        c = TAKEN_CHAR;
                        break;
                    default:
                        System.err.println("Unknown array value: " + val);
                        c = UNKNOWN_CHAR;
                        break;
                }
                str += c;
            }
            str += '\n';
        }
        return str;
    }

    public static Floor fromStrings(List<String> lines) {
        int height = lines.size();
        int width = lines.get(0).length();
        byte[] arr = new byte[width * height];
        int idx = 0;
        for (int y = 0; y < height; y++) {
            char[] line = lines.get(y).toCharArray();
            for (int x = 0; x < width; x++, idx++) {
                char c = line[x];
                byte val;
                switch (c) {
                    case FLOOR_CHAR:
                        val = FLOOR;
                        break;
                    case EMPTY_CHAR:
                        val = EMPTY;
                        break;
                    case TAKEN_CHAR:
                        val = TAKEN;
                        break;
                    default:
                        System.err.println("Unknown character: '" + c + "'");
                        val = UNKNOWN;
                        break;
                }
                arr[idx] = val;
            }
        }
        return new Floor(arr, width, height);
    }
}
