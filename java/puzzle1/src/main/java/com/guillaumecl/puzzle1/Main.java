package com.guillaumecl.puzzle1;

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

    void part1() throws IOException {
        var lines = readLines();
        var ints = lines.stream().mapToInt(Integer::parseInt).toArray();
        int numInts = ints.length;
        for (int idx = 0; idx < numInts - 1; idx++) {
            int a = ints[idx];
            if (a > 2019) {
                continue;
            }
            for (int jdx = idx + 1; jdx < numInts; jdx++) {
                int b = ints[jdx];
                if (a + b == 2020) {
                    System.out.println("a: " + a + " b: " + b + " a*b: " + (a * b));
                    return;
                }
            }
        }
    }

	void part2() throws IOException {
        var lines = readLines();
        var ints = lines.stream().mapToInt(Integer::parseInt).toArray();
        int numInts = ints.length;
        for (int idx = 0; idx < numInts - 2; idx++) {
            int a = ints[idx];
            if (a > 2018) {
                continue;
            }
            for (int jdx = idx + 1; jdx < numInts - 1; jdx++) {
                int b = ints[jdx];
                if (a + b > 2019) {
                    continue;
                }
                for (int kdx = jdx + 1; kdx < numInts; kdx++) {
                    int c = ints[kdx];
                    if (a + b + c == 2020) {
                        System.out.println("a: " + a + " b: " + b + " c: " + c + " a*b*c: " + (a * b * c));
                        return;
                    }
                }

            }
        }
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
