package com.guillaumecl.puzzle2;

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
        /*var lines = splitLines("1-3 a: abcde\n" +
                "1-3 b: cdefg\n" +
                "2-9 c: ccccccccc");*/
        long numValid = lines.stream()
                .map(PasswordInstance::fromString)
                .filter(PasswordInstance::isValid)
                .count();
        System.out.println("Got " + numValid + " valid passwords.");
    }

	void part2() throws IOException {
        var lines = readLines();
        /*var lines = splitLines("1-3 a: abcde\n" +
                "1-3 b: cdefg\n" +
                "2-9 c: ccccccccc");*/
        long numValid = lines.stream()
                .map(PasswordInstance::fromString)
                .filter(PasswordInstance::isValid2)
                .count();
        System.out.println("Got " + numValid + " valid passwords.");
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
