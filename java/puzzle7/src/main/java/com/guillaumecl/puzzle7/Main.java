package com.guillaumecl.puzzle7;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
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

    private Set<String> findContainers(Map<String, Set<Rule>> rulesByDestination, Set<Rule> currentSet) {
        if (currentSet.isEmpty()) {
            return new HashSet<>();
        }

        HashSet<Rule> parents = new HashSet<>();
        for (Rule r : currentSet) {
            if (rulesByDestination.containsKey(r.getName())) {
                var dests = rulesByDestination.get(r.getName());
                parents.addAll(dests);
            }
        }

        var containers = findContainers(rulesByDestination, parents);
        containers.addAll(currentSet.stream().map(Rule::getName).collect(Collectors.toSet()));
        return containers;
    }

    private long countBags(String currName, Map<String, Rule> rulesByName) {
        var dests = rulesByName.get(currName).getDestinations();
        if (dests.isEmpty()) {
            return 1;
        }

        long total = 1;
        for (RuleDestination dest : dests) {
            long multiplicity = dest.getQuantity();
            long childCount = countBags(dest.getDestination(), rulesByName);
            total += multiplicity * childCount;
        }

        return total;
    }

    void part1() throws IOException {
        var lines = readLines();
        /*
        var lines = splitLines("light red bags contain 1 bright white bag, 2 muted yellow bags.\n" +
                "dark orange bags contain 3 bright white bags, 4 muted yellow bags.\n" +
                "bright white bags contain 1 shiny gold bag.\n" +
                "muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.\n" +
                "shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.\n" +
                "dark olive bags contain 3 faded blue bags, 4 dotted black bags.\n" +
                "vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.\n" +
                "faded blue bags contain no other bags.\n" +
                "dotted black bags contain no other bags.");
         */

        var rulesByName = lines.stream().map(Rule::fromString).collect(Collectors.toMap(Rule::getName, Function.identity()));
        Map<String, Set<Rule>> rulesByDestination = new HashMap<>();
        rulesByName.values().stream()
                .forEach(rule -> {
                    var destinations = rule.getDestinations();
                    for (var dest : destinations) {
                        var destName = dest.getDestination();
                        rulesByDestination.computeIfAbsent(destName, key -> new HashSet<>());
                        rulesByDestination.get(destName).add(rule);
                    }
                });

        var goldContainers = findContainers(rulesByDestination, Set.of(rulesByName.get("shiny gold")));
        System.out.println("Got " + (goldContainers.size() - 1) + " containers");
    }

	void part2() throws IOException {
        var lines = readLines();
        /*
        var lines = splitLines("shiny gold bags contain 2 dark red bags.\n" +
                "dark red bags contain 2 dark orange bags.\n" +
                "dark orange bags contain 2 dark yellow bags.\n" +
                "dark yellow bags contain 2 dark green bags.\n" +
                "dark green bags contain 2 dark blue bags.\n" +
                "dark blue bags contain 2 dark violet bags.\n" +
                "dark violet bags contain no other bags.");
         */

        var rulesByName = lines.stream().map(Rule::fromString).collect(Collectors.toMap(Rule::getName, Function.identity()));
        long total = countBags("shiny gold", rulesByName) - 1;
        System.out.println("Got " + total + " total bags.");
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
