package com.guillaumecl.puzzle4;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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

    private boolean isValid(Map<String, String> idMap) {
        var keys = idMap.keySet();
        return keys.containsAll(Arrays.asList("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"));
    }

    private boolean isReallyValid(Map<String, String> idMap) {
        var keys = idMap.keySet();
        if (!keys.containsAll(Arrays.asList("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"))) {
            return false;
        }
        int byr = Integer.parseUnsignedInt(idMap.get("byr"));
        if (byr < 1920 || byr > 2002) {
            return false;
        }
        int iyr = Integer.parseUnsignedInt(idMap.get("iyr"));
        if (iyr < 2010 || iyr > 2020) {
            return false;
        }
        int eyr = Integer.parseUnsignedInt(idMap.get("eyr"));
        if (eyr < 2020 || eyr > 2030) {
            return false;
        }

        String hgt = idMap.get("hgt");
        if (StringUtils.endsWith(hgt, "cm")) {
            int h = Integer.parseUnsignedInt(StringUtils.substringBeforeLast(hgt, "cm"));
            if (h < 150 || h > 193) {
                return false;
            }
        } else if (StringUtils.endsWith(hgt, "in")) {
            int h = Integer.parseUnsignedInt(StringUtils.substringBeforeLast(hgt, "in"));
            if (h < 59 || h > 76) {
                return false;
            }
        } else {
            return false;
        }

        String hcl = idMap.get("hcl");
        if (hcl.charAt(0) == '#') {
            String digits = StringUtils.substring(hcl, 1).trim();
            if (digits.length() != 6) {
                return false;
            }
            boolean isHexDigit = digits.chars().allMatch(chr -> ((chr >= '0') && (chr <= '9')) || ((chr >= 'a') && (chr <= 'f')));
            if (! isHexDigit) {
                return false;
            }
        } else {
            return false;
        }

        String ecl = idMap.get("ecl");
        var validEcl = Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
        if (! validEcl.contains(ecl)) {
            return false;
        }

        String pid = idMap.get("pid").trim();
        if (pid.length() != 9) {
            return false;
        }
        if (!StringUtils.isNumeric(pid)) {
            return false;
        }
        return true;
    }

    void part1() throws IOException {
        var str = Files.readString(Paths.get("sources.txt"));
        /*
        var str = "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd\n" +
                "byr:1937 iyr:2017 cid:147 hgt:183cm\n" +
                "\n" +
                "iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884\n" +
                "hcl:#cfa07d byr:1929\n" +
                "\n" +
                "hcl:#ae17e1 iyr:2013\n" +
                "eyr:2024\n" +
                "ecl:brn pid:760753108 byr:1931\n" +
                "hgt:179cm\n" +
                "\n" +
                "hcl:#cfa07d eyr:2025 pid:166559648\n" +
                "iyr:2011 ecl:brn hgt:59in";
         */
        var parts = StringUtils.splitByWholeSeparator(str, "\n\n");
        System.out.println("Found " + parts.length + " IDs.");
        long numValid = Arrays.stream(parts)
                .map(part -> StringUtils.split(part))
                .map(entries -> {
                    return Arrays.stream(entries)
                            .map(entry -> StringUtils.split(entry, ':'))
                            .collect(Collectors.toMap(entry -> entry[0], entry -> entry[1]));
                }).filter(this::isValid)
                .count();
        System.out.println("Found " + numValid + " valid IDs.");
    }

	void part2() throws IOException {
        var str = Files.readString(Paths.get("sources.txt"));
        /*
        var str = "eyr:1972 cid:100\n" +
                "hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926\n" +
                "\n" +
                "iyr:2019\n" +
                "hcl:#602927 eyr:1967 hgt:170cm\n" +
                "ecl:grn pid:012533040 byr:1946\n" +
                "\n" +
                "hcl:dab227 iyr:2012\n" +
                "ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277\n" +
                "\n" +
                "hgt:59cm ecl:zzz\n" +
                "eyr:2038 hcl:74454a iyr:2023\n" +
                "pid:3556412378 byr:2007";
         */
        /*
        var str = "pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980\n" +
                "hcl:#623a2f\n" +
                "\n" +
                "eyr:2029 ecl:blu cid:129 byr:1989\n" +
                "iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm\n" +
                "\n" +
                "hcl:#888785\n" +
                "hgt:164cm byr:2001 iyr:2015 cid:88\n" +
                "pid:545766238 ecl:hzl\n" +
                "eyr:2022\n" +
                "\n" +
                "iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719";
         */
        var parts = StringUtils.splitByWholeSeparator(str, "\n\n");
        System.out.println("Found " + parts.length + " IDs.");
        long numValid = Arrays.stream(parts)
                .map(part -> StringUtils.split(part))
                .map(entries -> {
                    return Arrays.stream(entries)
                            .map(entry -> StringUtils.split(entry, ':'))
                            .collect(Collectors.toMap(entry -> entry[0], entry -> entry[1]));
                }).filter(this::isReallyValid)
                .count();
        System.out.println("Found " + numValid + " valid IDs.");
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
