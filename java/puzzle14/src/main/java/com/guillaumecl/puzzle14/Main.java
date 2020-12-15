package com.guillaumecl.puzzle14;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.math.BigInteger;
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

    private long getZerosMask2(String line) {
        var maskStr = StringUtils.trim(StringUtils.split(line, '=')[1]);
        long mask = 0;
        for (int idx = 0; idx < maskStr.length(); idx++) {
            mask <<= 1;
            char c = maskStr.charAt(idx);
            if (c == '0') {
                mask += 1L;
            }
        }
        return mask & 0xFFFFFFFFFL;
    }

    private long getZerosMask(String line) {
        var mask = getZerosMask2(line);
        return (~mask) & 0xFFFFFFFFFL;
    }

    private long getOnesMask(String line) {
        var maskStr = StringUtils.trim(StringUtils.split(line, '=')[1]);
        long mask = 0;
        for (int idx = 0; idx < maskStr.length(); idx++) {
            mask <<= 1;
            char c = maskStr.charAt(idx);
            if (c == '1') {
                mask += 1L;
            }
        }
        return mask & 0xFFFFFFFFFL;
    }

    private long getXMask(String line) {
        var maskStr = StringUtils.trim(StringUtils.split(line, '=')[1]);
        long mask = 0;
        for (int idx = 0; idx < maskStr.length(); idx++) {
            mask <<= 1;
            char c = maskStr.charAt(idx);
            if (c == 'X') {
                mask += 1L;
            }
        }
        return mask & 0xFFFFFFFFFL;
    }

    private void updateMemory(String line, Map<Long, Long> memory, long onesMask, long zerosMask) {
        var parts = StringUtils.splitByWholeSeparator(line, " = ");
        var addrStr = StringUtils.substring(parts[0], 4, parts[0].length() - 1);
        var valueStr = parts[1];
        var addr = Long.parseUnsignedLong(addrStr);
        var value = Long.parseUnsignedLong(valueStr);

        var maskedValue = (value & zerosMask) | onesMask;
        memory.put(addr, maskedValue);
    }

    void dumpMemory(Map<Long, Long> memory) {
        System.out.println("Memory (size " + memory.size() + "):");
        for (var entry : memory.entrySet()) {
            System.out.println("mem[" + entry.getKey() + "] = " + entry.getValue());
        }
    }

    void part1() throws IOException {
        var lines = readLines();
        /*
        var lines = splitLines("mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X\n" +
                "mem[8] = 11\n" +
                "mem[7] = 101\n" +
                "mem[8] = 0");
         */
        long zerosMask = 0L;
        long onesMask = 1L;
        Map<Long, Long> memory = new HashMap<>();
        for (String line : lines) {
            if (StringUtils.startsWith(line, "mask")) {
                zerosMask = getZerosMask(line);
                onesMask = getOnesMask(line);
            } else {
                updateMemory(line, memory, onesMask, zerosMask);
            }
        }

        long total = 0L;
        for (var entry : memory.entrySet()) {
            total += (entry.getValue() & 0xFFFFFFFFFL);
        }
        System.out.println("Total: " + total);
    }

    private long computeAddr(long orig, long bits, long mask) {
        int bitsIdx = 0;
        long val = orig;
        for (int idx = 0; idx <= 36; idx++) {
            if ((mask & 1L) == 1L) {
                long bit = (bits >> bitsIdx) & 1;
                long bitMask = 1L << idx;
                if (bit == 0L) {
                    val &= ~bitMask;
                } else {
                    val |= bitMask;
                }
                bitsIdx++;
            }
            mask >>= 1;
        }
        return val;
    }

    private void updateMemory2(String line, Map<Long, Long> memory, long onesMask, long xMask) {
        var parts = StringUtils.splitByWholeSeparator(line, " = ");
        var addrStr = StringUtils.substring(parts[0], 4, parts[0].length() - 1);
        var valueStr = parts[1];
        var rawAddr = Long.parseUnsignedLong(addrStr);
        var value = Long.parseUnsignedLong(valueStr);
        var baseAddr = rawAddr | onesMask;

        int numFloat = Long.bitCount(xMask);
        if (numFloat == 0) {
            memory.put(baseAddr, value);
            return;
        }

        long maxCount = 1 << numFloat;
        for (long c = 0L; c < maxCount; c++) {
            var addr = computeAddr(baseAddr, c, xMask);
            memory.put(addr, value);
        }
    }

    void part2() throws IOException {
        var lines = readLines();
        /*
        var lines = splitLines("mask = X00000000000000000000000000000000000\n" +
                "mem[42] = 100\n" +
                "mask = 00000000000000000000000000000000X0XX\n" +
                "mem[26] = 1");
         */
        long maxMask = 0L;
        int maxBits = 0;
        long onesMask = 0L;
        long xMask = 0L;
        Map<Long, Long> memory = new HashMap<>();
        for (String line : lines) {
            if (StringUtils.startsWith(line, "mask")) {
                onesMask = getOnesMask(line);
                xMask = getXMask(line);
                maxBits = Math.max(maxBits, Long.bitCount(xMask));
                maxMask = Long.compareUnsigned(maxMask, xMask) == -1 ? xMask : maxMask;
            } else {
                updateMemory2(line, memory, onesMask, xMask);
            }
        }

        System.out.println("Max X count: " + maxBits);
        System.out.println("Max Mask: " + Long.toBinaryString(maxMask));
        System.out.println("Memory set size: " + memory.size());
        // 1100 0100 0000 0010 0100 0001 0000 1000 1100
        long total = 0L;
        for (var entry : memory.entrySet()) {
            total += entry.getValue();
        }
        System.out.println("Total: " + Long.toUnsignedString(total));
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
