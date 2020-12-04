package com.guillaumecl.puzzle3;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    private List<String> readLines() throws IOException {
        return Files.readAllLines(Paths.get("sources.txt"))
                       .stream()
                       .map(StringUtils::trim)
                       .filter(s -> ! StringUtils.isBlank(s))
                       .collect(Collectors.toList());
    }

    private List<String> splitLines(String input) {
        return Arrays.stream(StringUtils.split(input, '\n'))
                .map(String::trim)
                .filter(s -> !StringUtils.isBlank(s))
                .collect(Collectors.toList());
    }

    private List<Boolean> getTreeRow(String line) {
        return line.chars().mapToObj(chr -> chr == '#').collect(Collectors.toList());
    }

    private List<List<Boolean>> getTreesMap(List<String> lines) {
        return lines.stream().map(this::getTreeRow).collect(Collectors.toList());
    }

    private void dumpMap(List<List<Boolean>> treeMap) {
        for (int y = 0; y < treeMap.size(); y++) {
            var line = treeMap.get(y);
            for (int x = 0; x < line.size(); x++) {
                System.out.print(line.get(x) ? '#' : '.');
            }
            System.out.println();
        }
    }

    long countTrees(int stepX, int stepY, List<List<Boolean>> map) {
        int height = map.size();
        int width = map.get(0).size();
        int x = 0;
        int y = 0;
        long count = 0;
        while (y < height) {
            if (map.get(y).get(x)) {
                count++;
            }
            x = (x + stepX) % width;
            y += stepY;
        }
        return count;
    }

    void part1() throws IOException {
        var lines = readLines();
        /*
        var lines = splitLines("..##.......\n" +
                "#...#...#..\n" +
                ".#....#..#.\n" +
                "..#.#...#.#\n" +
                ".#...##..#.\n" +
                "..#.##.....\n" +
                ".#.#.#....#\n" +
                ".#........#\n" +
                "#.##...#...\n" +
                "#...##....#\n" +
                ".#..#...#.#");
         */
        var map = getTreesMap(lines);
        long numTrees = countTrees(3, 1, map);
        System.out.println("Got " + numTrees + " trees.");
    }

	void part2() throws IOException {
        var lines = readLines();
        /*
        var lines = splitLines("..##.......\n" +
                "#...#...#..\n" +
                ".#....#..#.\n" +
                "..#.#...#.#\n" +
                ".#...##..#.\n" +
                "..#.##.....\n" +
                ".#.#.#....#\n" +
                ".#........#\n" +
                "#.##...#...\n" +
                "#...##....#\n" +
                ".#..#...#.#");
         */
        var map = getTreesMap(lines);
        long numTrees11 = countTrees(1, 1, map);
        System.out.println("Got " + numTrees11 + " trees.");
        long numTrees31 = countTrees(3, 1, map);
        System.out.println("Got " + numTrees31 + " trees.");
        long numTrees51 = countTrees(5, 1, map);
        System.out.println("Got " + numTrees51 + " trees.");
        long numTrees71 = countTrees(7, 1, map);
        System.out.println("Got " + numTrees71 + " trees.");
        long numTrees12 = countTrees(1, 2, map);
        System.out.println("Got " + numTrees12 + " trees.");

        long multiple = numTrees11 * numTrees31 * numTrees51 * numTrees71 * numTrees12;
        System.out.println("Multiple: " + multiple);
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
