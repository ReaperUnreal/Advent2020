package com.guillaumecl.puzzle17;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private List<String> readLines(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName))
                .stream()
                .map(StringUtils::trim)
                .filter(s -> !StringUtils.isBlank(s))
                .collect(Collectors.toList());
    }

    private List<String> readLines() throws IOException {
        return readLines("sources.txt");
    }

    private List<String> splitLines(String input) {
        return Arrays.stream(StringUtils.split(input, '\n'))
                .map(String::trim)
                .filter(s -> !StringUtils.isBlank(s))
                .collect(Collectors.toList());
    }

    private List<Integer> getIntegerList(String line) {
        return Arrays.stream(StringUtils.split(line, ','))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private void dumpGrid(boolean[] arr, int width, int height) {
        int depth = arr.length / (width * height);
        int idx = 0;
        for (int z = 0; z < depth; z++) {
            System.out.println("z=" + z);
            for (int y = 0; y < height; y++) {
                String str = "";
                for (int x = 0; x < width; x++, idx++) {
                    str += arr[idx] ? '#' : '.';
                }
                System.out.println(str);
            }
            System.out.println();
        }
    }

    private int coordToIndex(int x, int y, int z, int width, int height) {
        return x + y * width + z * width * height;
    }

    private int coordToIndex(int x, int y, int z, int w, int width, int height, int depth) {
        return x + y * width + z * width * height + w * width * height * depth;
    }

    private boolean[] parseGrid(List<String> lines) {
        // z is always 0 for initial read
        int z = 0;

        int height = lines.size();
        int width = lines.get(0).length();
        var arr = new boolean[width * height];
        for (int y = 0; y < height; y++) {
            String line = lines.get(y);
            for (int x = 0; x < width; x++) {
                char c = line.charAt(x);
                int idx = coordToIndex(x, y, z, width, height);
                arr[idx] = (c == '#');
            }
        }
        return arr;
    }

    private boolean[] growGrid(boolean[] orig, int width, int height) {
        int depth = orig.length / (width * height);

        int newDepth = depth + 2;
        int newWidth = width + 2;
        int newHeight = height + 2;

        boolean grid[] = new boolean[newWidth * newHeight * newDepth];

        Arrays.fill(grid, false);
        for (int z = 0; z < depth; z++) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int origIdx = coordToIndex(x, y, z, width, height);
                    int destIdx = coordToIndex(x + 1, y + 1, z + 1, newWidth, newHeight);
                    grid[destIdx] = orig[origIdx];
                }
            }
        }
        return grid;
    }

    private boolean[] growGrid(boolean[] orig, int width, int height, int depth) {
        int spissitude = orig.length / (width * height * depth);

        int newSpiss = spissitude + 2;
        int newDepth = depth + 2;
        int newHeight = height + 2;
        int newWidth = width + 2;

        boolean grid[] = new boolean[newWidth * newHeight * newDepth * newSpiss];

        Arrays.fill(grid, false);
        for (int w = 0; w < spissitude; w++) {
            for (int z = 0; z < depth; z++) {
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        int origIdx = coordToIndex(x, y, z, w, width, height, depth);
                        int destIdx = coordToIndex(x + 1, y + 1, z + 1, w + 1, newWidth, newHeight, newDepth);
                        grid[destIdx] = orig[origIdx];
                    }
                }
            }
        }

        return grid;
    }

    private int countNeighbours(int x, int y, int z, boolean[] grid, int width, int height, int depth) {
        int total = 0;
        for (int zOff = -1; zOff <= 1; zOff++) {
            int currZ = z + zOff;
            if (currZ < 0 || currZ >= depth) {
                continue;
            }
            for (int yOff = -1; yOff <= 1; yOff++) {
                int currY = y + yOff;
                if (currY < 0 || currY >= height) {
                    continue;
                }
                for (int xOff = -1; xOff <= 1; xOff++) {
                    int currX = x + xOff;
                    if (currX < 0 || currX >= width) {
                        continue;
                    }
                    if (xOff == 0 && yOff == 0 && zOff == 0) {
                        continue;
                    }
                    int currIdx = coordToIndex(currX, currY, currZ, width, height);
                    total += grid[currIdx] ? 1 : 0;
                }
            }
        }
        return total;
    }

    private int countNeighbours(int x, int y, int z, int w, boolean[] grid, int width, int height, int depth, int spissitude) {
        int total = 0;
        for (int wOff = -1; wOff <= 1; wOff++) {
            int currW = w + wOff;
            if (currW < 0 || currW >= spissitude) {
                continue;
            }
            for (int zOff = -1; zOff <= 1; zOff++) {
                int currZ = z + zOff;
                if (currZ < 0 || currZ >= depth) {
                    continue;
                }
                for (int yOff = -1; yOff <= 1; yOff++) {
                    int currY = y + yOff;
                    if (currY < 0 || currY >= height) {
                        continue;
                    }
                    for (int xOff = -1; xOff <= 1; xOff++) {
                        int currX = x + xOff;
                        if (currX < 0 || currX >= width) {
                            continue;
                        }
                        if (xOff == 0 && yOff == 0 && zOff == 0 && wOff == 0) {
                            continue;
                        }
                        int currIdx = coordToIndex(currX, currY, currZ, currW, width, height, depth);
                        total += grid[currIdx] ? 1 : 0;
                    }
                }
            }
        }
        return total;
    }

    private boolean[] advanceGrid(boolean[] orig, int width, int height) {
        var grid = growGrid(orig, width, height);
        var steppedGrid = ArrayUtils.clone(grid);
        int newWidth = width + 2;
        int newHeight = height + 2;
        int newDepth = grid.length / (newWidth * newHeight);

        for (int z = 0, idx = 0; z < newDepth; z++) {
            for (int y = 0; y < newHeight; y++) {
                for (int x = 0; x < newWidth; x++, idx++) {
                    int numNeighbours = countNeighbours(x, y, z, grid, newWidth, newHeight, newDepth);
                    boolean oldState = grid[idx];
                    boolean newState;
                    if (oldState) {
                        if (numNeighbours == 2 || numNeighbours == 3) {
                            newState = true;
                        } else {
                            newState = false;
                        }
                    } else {
                        if (numNeighbours == 3) {
                            newState = true;
                        } else {
                            newState = false;
                        }
                    }
                    steppedGrid[idx] = newState;
                }
            }
        }

        return steppedGrid;
    }

    private boolean[] advanceGrid(boolean[] orig, int width, int height, int depth) {
        var grid = growGrid(orig, width, height, depth);
        var steppedGrid = ArrayUtils.clone(grid);
        int newWidth = width + 2;
        int newHeight = height + 2;
        int newDepth = depth + 2;
        int newSpiss = grid.length / (newWidth * newHeight * newDepth);

        for (int w = 0, idx = 0; w < newSpiss; w++) {
            for (int z = 0; z < newDepth; z++) {
                for (int y = 0; y < newHeight; y++) {
                    for (int x = 0; x < newWidth; x++, idx++) {
                        int numNeighbours = countNeighbours(x, y, z, w, grid, newWidth, newHeight, newDepth, newSpiss);
                        boolean oldState = grid[idx];
                        boolean newState;
                        if (oldState) {
                            if (numNeighbours == 2 || numNeighbours == 3) {
                                newState = true;
                            } else {
                                newState = false;
                            }
                        } else {
                            if (numNeighbours == 3) {
                                newState = true;
                            } else {
                                newState = false;
                            }
                        }
                        steppedGrid[idx] = newState;
                    }
                }
            }
        }

        return steppedGrid;
    }

    private int countGrid(boolean[] grid) {
        int total = 0;
        for (boolean b : grid) {
            total += b ? 1 : 0;
        }
        return total;
    }

    void part1() throws IOException {
        var lines = readLines();
        /*
        var lines = splitLines(".#.\n" +
                "..#\n" +
                "###");
         */
        int height = lines.size();
        int width = lines.get(0).length();
        var grid = parseGrid(lines);
        for (int idx = 0; idx < 6; idx++) {
            var newGrid = advanceGrid(grid, width, height);
            width += 2;
            height += 2;
            grid = newGrid;
        }
        System.out.println("Total: " + countGrid(grid));
    }

    void part2() throws IOException {
        var lines = readLines();
        /*
        var lines = splitLines(".#.\n" +
                "..#\n" +
                "###");
         */
        int height = lines.size();
        int width  = lines.get(0).length();
        int depth = 1;
        var grid = parseGrid(lines);
        for (int idx = 0; idx < 6; idx++) {
            var newGrid = advanceGrid(grid, width, height, depth);
            width += 2;
            height += 2;
            depth += 2;
            grid = newGrid;
        }
        System.out.println("Total: " + countGrid(grid));
    }

    public static void main(String[] args) {
        var main = new Main();
        try {
            main.part2();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
