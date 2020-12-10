package com.guillaumecl.puzzle10;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    private List<String> readLines() throws IOException {
        return Files.readAllLines(Paths.get("sources.txt"))
                       .stream()
                       .map(StringUtils::trim)
                       .filter(s -> ! StringUtils.isBlank(s))
                       .collect(Collectors.toList());
    }

    private int[] readInts() throws IOException {
        return Files.readAllLines(Paths.get("sources.txt"))
                .stream()
                .map(StringUtils::trim)
                .filter(s -> ! StringUtils.isBlank(s))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private List<String> splitLines(String input) {
        return Arrays.stream(StringUtils.split(input, '\n'))
                .map(String::trim)
                .filter(s -> !StringUtils.isBlank(s))
                .collect(Collectors.toList());
    }

    private int[] splitInts(String input) {
        return Arrays.stream(StringUtils.split(input, '\n'))
                .map(String::trim)
                .filter(s -> !StringUtils.isBlank(s))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private Tuple3<Integer, Integer, Integer> findDifferences(int[] list) {
        Arrays.sort(list);
        int[] diffs = {0, 0, 0, 1};
        int prev = 0;
        for (int idx = 0; idx < list.length; idx++) {
            int curr = list[idx];
            int diff = curr - prev;
            prev = curr;
            diffs[diff]++;
        }
        return Tuple.tuple(diffs[1], diffs[2], diffs[3]);
    }

    void part1() throws IOException {
        /*
        var list = splitInts("16\n" +
                "10\n" +
                "15\n" +
                "5\n" +
                "1\n" +
                "11\n" +
                "7\n" +
                "19\n" +
                "6\n" +
                "12\n" +
                "4");
         */
        var list = readInts();
        var diffs = findDifferences(list);
        System.out.println("1 differences: " + diffs.v1 + " 3 differences: " + diffs.v3 + " Multiple: " + (diffs.v1 * diffs.v3));
    }

    private long countWays(int[] list) {
        Arrays.sort(list);
        ArrayUtils.reverse(list);
        int target = list[0] + 3;
        long[] counts = new long[target + 1];
        counts[target] = 1L;

        for (int idx = 0; idx < list.length; idx++) {
            int curr = list[idx];
            for (int offset = 1; offset <= 3; offset++) {
                counts[curr] += counts[curr + offset];
            }
        }

        return counts[0];
    }

	void part2() throws IOException {
        /*
        var lines = splitLines("16\n" +
                "10\n" +
                "15\n" +
                "5\n" +
                "1\n" +
                "11\n" +
                "7\n" +
                "19\n" +
                "6\n" +
                "12\n" +
                "4");
         */
        var lines = readLines();
        var list = IntStream.concat(IntStream.of(0),
                lines.stream()
                .mapToInt(Integer::parseInt))
                .toArray();
        long count = countWays(list);
        System.out.println("Num ways: " + count);
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
