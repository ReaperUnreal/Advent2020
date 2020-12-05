package com.guillaumecl.puzzle5;

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

    private int stringToInt(String str) {
        var binaryStr = StringUtils.replaceEach(str, new String[]{"B", "F", "R", "L"}, new String[]{"1", "0", "1", "0"});
        return Integer.parseUnsignedInt(binaryStr, 2);
    }

    private void testString(String str) {
        System.out.println("\"" + str + "\" = " + stringToInt(str));
    }

    void part1() throws IOException {
        int max = Files.readAllLines(Paths.get("sources.txt"))
                .stream()
                .map(StringUtils::trim)
                .filter(s -> ! StringUtils.isBlank(s))
                .mapToInt(this::stringToInt)
                .max()
                .orElse(-1);
        System.out.println("Max seat ID: " + max);
    }

	void part2() throws IOException {
        int[] seatIDs = readLines().stream()
                .mapToInt(this::stringToInt)
                .sorted()
                .toArray();
        int offset = seatIDs[0];
        for (int idx = 0; idx < seatIDs.length; idx++) {
            if (seatIDs[idx] != idx + offset) {
                System.out.println("Found missing seat: " + (idx + offset));
                break;
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
