package com.guillaumecl.puzzle15;

import org.apache.commons.lang3.StringUtils;

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

    private List<Integer> getIntegerList(String line) {
        return Arrays.stream(StringUtils.split(line, ','))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private Map<Integer, Integer> computeStartingMap(List<Integer> numbers) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int idx = 0; idx < numbers.size() - 1; idx++) {
            int num = numbers.get(idx);
            map.put(num, idx + 1);
        }
        return map;
    }

    private int runWithList(String line, int max) {
        var numbers = getIntegerList(line);
        var map = computeStartingMap(numbers);

        int turnIdx = numbers.size();
        int prevNum = numbers.get(numbers.size() - 1);
        while (true) {
            if (map.containsKey(prevNum)) {
                int prevIdx = map.get(prevNum);
                map.put(prevNum, turnIdx);
                int diff = turnIdx - prevIdx;
                prevNum = diff;
            } else {
                map.put(prevNum, turnIdx);
                prevNum = 0;
            }
            turnIdx++;
            if (turnIdx == max) {
                break;
            }
        }
        System.out.println("Result: " + prevNum);
        return prevNum;
    }

    void part1() throws IOException {
        runWithList("0,3,6", 2020);
        runWithList("1,3,2", 2020);
        runWithList("2,1,3", 2020);
        runWithList("1,2,3", 2020);
        runWithList("2,3,1", 2020);
        runWithList("3,2,1", 2020);
        runWithList("3,1,2", 2020);
        runWithList("13,16,0,12,15,1", 2020);
    }

    void part2() throws IOException {
        runWithList("0,3,6", 30000000);
        runWithList("1,3,2", 30000000);
        runWithList("2,1,3", 30000000);
        runWithList("1,2,3", 30000000);
        runWithList("2,3,1", 30000000);
        runWithList("3,2,1", 30000000);
        runWithList("3,1,2", 30000000);
        runWithList("13,16,0,12,15,1", 30000000);
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
