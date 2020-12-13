package com.guillaumecl.puzzle13;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    private List<String> readLines() throws IOException {
        return Files.readAllLines(Paths.get("sources.txt"))
                .stream()
                .map(StringUtils::trim)
                .filter(s -> !StringUtils.isBlank(s))
                .collect(Collectors.toList());
    }

    private int[] readInts() throws IOException {
        return Files.readAllLines(Paths.get("sources.txt"))
                .stream()
                .map(StringUtils::trim)
                .filter(s -> !StringUtils.isBlank(s))
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

    private int[] getLineIDs(String str) {
        return Arrays.stream(StringUtils.split(str, ','))
                .filter(part -> !StringUtils.equalsIgnoreCase(part, "x"))
                .mapToInt(Integer::parseUnsignedInt)
                .toArray();
    }

    private void dumpIDs(int[] ids) {
        System.out.println(Arrays.stream(ids).mapToObj(Integer::toUnsignedString).collect(Collectors.joining(", ")));
    }

    void part1() throws IOException {
        var lines = readLines();
        /*
        var lines = splitLines("939\n" +
                "7,13,x,x,59,x,31,19");
         */
        int earliestTimestamp = Integer.parseUnsignedInt(lines.get(0));
        int[] ids = getLineIDs(lines.get(1));
        dumpIDs(ids);
        int timestamp = earliestTimestamp;
        int foundId = -1;
        while (foundId == -1) {
            for (int idx = 0; idx < ids.length; idx++) {
                int id = ids[idx];
                if (timestamp % id == 0) {
                    timestamp--;
                    foundId = id;
                    break;
                }
            }
            timestamp++;
        }

        int diff = timestamp - earliestTimestamp;
        System.out.println("Timestamp: " + timestamp + " Diff: " + diff + " ID: " + foundId + " Result: " + (diff * foundId));
    }

    private List<Pair<Integer, Integer>> getIDAndOffset(String str) {
        var rawList = StringUtils.split(str, ',');
        return IntStream.range(0, rawList.length)
                .mapToObj(idx -> Pair.of(rawList[idx], idx))
                .filter(pair -> !StringUtils.equalsIgnoreCase("x", pair.getLeft()))
                .map(pair -> Pair.of(Integer.parseUnsignedInt(pair.getLeft()), pair.getRight()))
                .sorted((a, b) -> Integer.compareUnsigned(b.getLeft(), a.getLeft()))
                .collect(Collectors.toList());
    }

    private String pairToString(Pair<Integer, Integer> pair) {
        return "(" + pair.getLeft() + ", " + pair.getRight() + ")";
    }

    private void dumpPairs(List<Pair<Integer, Integer>> pairs) {
        var str = pairs.stream()
                .map(this::pairToString)
                .collect(Collectors.joining(", "));
        System.out.println(str);
    }

    private long maxOffset(List<Pair<Integer, Integer>> pairs) {
        return pairs.stream()
                .mapToLong(Pair::getRight)
                .max()
                .orElse(-1);
    }

    private void part2WithString(String str) {
        var pairs = getIDAndOffset(str);
        dumpPairs(pairs);
        long maxOffset = maxOffset(pairs);
        System.out.println("Max Offset: " + maxOffset);
        long[] ns = pairs.stream().mapToLong(Pair::getLeft).toArray();
        long[] as = pairs.stream().mapToLong(Pair::getRight).map(a -> maxOffset - a).toArray();
        long result = ChineseRemainderTheorem.chineseRemainder(ns, as) - maxOffset;
        System.out.println("Result: " + Long.toUnsignedString(result));
    }

    void part2() throws IOException {
        var lines = readLines();
        /*
        var lines = splitLines("939\n" +
                "1789,37,47,1889");
         */
        part2WithString("17,x,13,19");
        part2WithString("67,7,59,61");
        part2WithString("67,x,7,59,61");
        part2WithString("67,7,x,59,61");
        part2WithString("1789,37,47,1889");
        part2WithString(lines.get(1));
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
