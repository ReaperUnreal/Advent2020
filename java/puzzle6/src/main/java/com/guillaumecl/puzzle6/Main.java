package com.guillaumecl.puzzle6;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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

    private int getGroupCount(String[] group) {
        Map<Integer, Boolean> answerMap = new HashMap<>();
        for (String person : group) {
            var str = StringUtils.trim(person);
            str.chars().forEach(c -> {
                answerMap.computeIfAbsent(c, val -> true);
            });
        }
        return answerMap.size();
    }

    private int getGroupCountPart2(String[] group) {
        Map<Integer, Integer> answerMap = new HashMap<>();
        int groupSize = group.length;
        for (String person : group) {
            var str = StringUtils.trim(person);
            str.chars().forEach(c -> {
                answerMap.compute(c, (k, v) -> {
                    if (v == null) {
                        return 1;
                    } else {
                        return v + 1;
                    }
                });
            });
        }
        var total = (int)answerMap.values().stream()
                .filter(count -> count == groupSize)
                .count();
        System.out.println("Group count: " + total);
        return total;
    }

    void part1() throws IOException {
        var file = Files.readString(Paths.get("sources.txt"));
        /*
        var file = "abc\n" +
                "\n" +
                "a\n" +
                "b\n" +
                "c\n" +
                "\n" +
                "ab\n" +
                "ac\n" +
                "\n" +
                "a\n" +
                "a\n" +
                "a\n" +
                "a\n" +
                "\n" +
                "b";
         */
        var groups = StringUtils.splitByWholeSeparator(file, "\n\n");
        int count = Arrays.stream(groups)
                .map(StringUtils::split)
                .mapToInt(this::getGroupCount)
                .sum();
        System.out.println("Total: " + count);
    }

	void part2() throws IOException {
        var file = Files.readString(Paths.get("sources.txt"));
        /*
        var file = "abc\n" +
                "\n" +
                "a\n" +
                "b\n" +
                "c\n" +
                "\n" +
                "ab\n" +
                "ac\n" +
                "\n" +
                "a\n" +
                "a\n" +
                "a\n" +
                "a\n" +
                "\n" +
                "b";
         */
        var groups = StringUtils.splitByWholeSeparator(file, "\n\n");
        int count = Arrays.stream(groups)
                .map(StringUtils::split)
                .mapToInt(this::getGroupCountPart2)
                .sum();
        System.out.println("Total: " + count);
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
