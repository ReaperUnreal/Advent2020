package com.guillaumecl.puzzle9;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
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

    private boolean isExpressibleAsSum(List<Long> source, long target) {
        int numEntries = source.size();
        for (int idx = 0; idx < numEntries - 1; idx++) {
            long iVal = source.get(idx);
            for (int jdx = idx + 1; jdx < numEntries; jdx++) {
                long jVal = source.get(jdx);
                if (iVal + jVal == target) {
                    return true;
                }
            }
        }
        return false;
    }

    private long verifyList(long[] list, int preambleSize) {
        if (list.length <= preambleSize) {
            return -1;
        }
        List<Long> pool = new ArrayList<>();
        pool.add(0L);
        for (int idx = 0; idx < (preambleSize - 1); idx++) {
            pool.add(list[idx]);
        }
        for (int idx = preambleSize; idx < list.length; idx++) {
            pool.remove(0);
            pool.add(list[idx - 1]);
            if (!isExpressibleAsSum(pool, list[idx])) {
                return list[idx];
            }
        }
        return -1;
    }

    void part1() throws IOException {
        var lines = readLines();
        /*
        var lines = splitLines("35\n" +
                "20\n" +
                "15\n" +
                "25\n" +
                "47\n" +
                "40\n" +
                "62\n" +
                "55\n" +
                "65\n" +
                "95\n" +
                "102\n" +
                "117\n" +
                "150\n" +
                "182\n" +
                "127\n" +
                "219\n" +
                "299\n" +
                "277\n" +
                "309\n" +
                "576");
         */
        var list = lines.stream().mapToLong(Long::parseLong).toArray();
        long invalidItem = verifyList(list, 25);
        System.out.println("First invalid item: " + invalidItem);
    }

    private Pair<Integer, Integer> findRange(long[] arr, long target) {
        int startIdx = 0;
        int endIdx = 0;
        long runningTotal = arr[0];
        while (true) {
            if (endIdx >= arr.length || startIdx >= arr.length) {
                return Pair.of(0, 0);
            }
            if (runningTotal == target) {
                return Pair.of(startIdx, endIdx);
            } else if (runningTotal < target) {
                endIdx++;
                runningTotal += arr[endIdx];
            } else {
                runningTotal -= arr[startIdx];
                startIdx++;
            }
        }
    }

	void part2() throws IOException {
        /*
        var lines = splitLines("35\n" +
                "20\n" +
                "15\n" +
                "25\n" +
                "47\n" +
                "40\n" +
                "62\n" +
                "55\n" +
                "65\n" +
                "95\n" +
                "102\n" +
                "117\n" +
                "150\n" +
                "182\n" +
                "127\n" +
                "219\n" +
                "299\n" +
                "277\n" +
                "309\n" +
                "576");
         */

        var lines = readLines();
        var list = lines.stream().mapToLong(Long::parseLong).toArray();
        //var range = findRange(list, 127L);
        var range = findRange(list, 133015568L);
        System.out.println("Range: " + range.getLeft() + ".." + range.getRight());
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;
        for (int idx = range.getLeft(); idx <= range.getRight(); idx++) {
            long val = list[idx];
            min = Math.min(min, val);
            max = Math.max(max, val);
        }
        System.out.println("Min: " + min + " Max: " + max + " Total: " + (min + max));
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
