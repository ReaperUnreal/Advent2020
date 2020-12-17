package com.guillaumecl.puzzle16;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private List<String> readLines(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName))
                .stream()
                .map(StringUtils::trim)
                .filter(s -> !StringUtils.isBlank(s))
                .collect(Collectors.toList());
    }

    private List<String> readLines() throws IOException {
        return readLines("sources.txt");
    }

    private List<String> splitLines(String input) {
        return Arrays.stream(StringUtils.split(input, '\n'))
                .map(String::trim)
                .filter(s -> !StringUtils.isBlank(s))
                .collect(Collectors.toList());
    }

    private List<Integer> getIntegerList(String line) {
        return Arrays.stream(StringUtils.split(line, ','))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private List<StatsRange> getRanges(List<String> lines) {
        return lines.stream()
                .map(StatsRange::fromString)
                .collect(Collectors.toList());
    }

    private boolean testValue(int val, List<StatsRange> statsRanges) {
        return statsRanges.stream().anyMatch(sr -> sr.isValidValue(val));
    }

    private boolean isTicketValid(List<Integer> ticket, List<StatsRange> statsRanges) {
        return ticket.stream().allMatch(val -> testValue(val, statsRanges));
    }

    private List<List<Integer>> getValidTickets(List<List<Integer>> tickets, List<StatsRange> statsRanges) {
        return tickets.stream()
                .filter(ticket -> isTicketValid(ticket, statsRanges))
                .collect(Collectors.toList());
    }

    private long testTicket(List<Integer> ticket, List<StatsRange> statsRanges) {
        long total = 0L;
        for (var val : ticket) {
            if (!testValue(val, statsRanges)) {
                System.out.println("Value " + val + " is invalid.");
                total += val;
            }
        }
        return total;
    }

    private List<Integer> readTicket(String line) {
        return Arrays.stream(StringUtils.split(line, ',')).map(Integer::parseInt).collect(Collectors.toList());
    }

    private List<List<Integer>> readTickets(List<String> lines) {
        return lines.stream()
                .map(this::readTicket)
                .collect(Collectors.toList());
    }

    private void dumpTicket(List<Integer> ticket) {
        var str = ticket.stream().map(i -> i.toString()).collect(Collectors.joining(","));
        System.out.println("[" + str + "]");
    }

    private void dumpTickets(List<List<Integer>> tickets) {
        tickets.forEach(this::dumpTicket);
    }

    void part1() throws IOException {
        var statsLines = readLines("stats.txt");
        /*
        var statsLines = splitLines("class: 1-3 or 5-7\n" +
                "row: 6-11 or 33-44\n" +
                "seat: 13-40 or 45-50");
         */
        var ranges = getRanges(statsLines);
        ranges.forEach(System.out::println);

        var ticketLines = readLines("nearbytickets.txt");
        /*
        var ticketLines = splitLines("7,3,47\n" +
                "40,4,50\n" +
                "55,2,20\n" +
                "38,6,12");
         */
        var tickets = readTickets(ticketLines);
        dumpTickets(tickets);

        long total = tickets.stream().mapToLong(ticket -> testTicket(ticket, ranges)).sum();
        System.out.println("Total: " + total);
    }

    private boolean isStatsRangeValidForColumn(int columnIdx, List<List<Integer>> tickets, StatsRange range) {
        return tickets.stream()
                .mapToInt(ticket -> ticket.get(columnIdx))
                .allMatch(range::isValidValue);
    }

    private List<StatsRange> getRangesValidForColumn(int columnIdx, List<List<Integer>> tickets, List<StatsRange> statsRanges) {
        return statsRanges.stream()
                .filter(range -> isStatsRangeValidForColumn(columnIdx, tickets, range))
                .collect(Collectors.toList());
    }

    private List<StatsRange> getValidRanges(List<StatsRange> ranges, Set<String> invalidNames) {
        return ranges.stream()
                .filter(range -> !invalidNames.contains(range.getName()))
                .collect(Collectors.toList());
    }

    void part2() throws IOException {
        var statsLines = readLines("stats.txt");
        var ranges = getRanges(statsLines);
        var ticketLines = readLines("nearbytickets.txt");
        var rawTickets = readTickets(ticketLines);
        var tickets = getValidTickets(rawTickets, ranges);

        // departure track: 6
        // departure station: 3
        // departure platform: 10
        // departure time: 19
        // departure location: 1
        // departure date: 8
        var departureIndices = Set.of(6, 3, 10, 19, 1, 8);
        Set<String> invalidRangeNames = Set.of("wagon", "duration", "arrival track", "type", "price", "route", "seat", "train", "departure track", "departure station", "departure platform", "departure time", "departure location", "departure date");
        var validRanges = getValidRanges(ranges, invalidRangeNames);

        Set<Integer> invalidColumns = Set.of(15, 18, 2, 17, 4, 7, 14, 9, 6, 3, 10, 19, 1, 8);

        int numColumns = tickets.get(0).size();
        System.out.println("Got " + numColumns + " columns.");
        for (int idx = 0; idx < numColumns; idx++) {
            if (invalidColumns.contains(idx)) {
                continue;
            }
            var validStats = getRangesValidForColumn(idx, tickets, validRanges);
            var namesStr = validStats.stream()
                    .map(StatsRange::getName)
                    .collect(Collectors.joining(", "));
            boolean isSingle = validStats.size() == 1;
            System.out.println("Column " + idx + ": " + namesStr + (isSingle ? "*" : ""));
        }

        var yourTicketLine = readLines("yourticket.txt").get(0);
        var yourTicket = readTicket(yourTicketLine);

        long total = 1L;
        for (var idx : departureIndices) {
            var val = yourTicket.get(idx);
            total *= val;
            System.out.println("Your Ticket[" + idx + "] = " + val);
        }
        System.out.println("Result: " + total);
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
