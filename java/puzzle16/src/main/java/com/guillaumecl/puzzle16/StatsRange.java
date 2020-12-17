package com.guillaumecl.puzzle16;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class StatsRange {
    private String name;
    private List<Pair<Integer, Integer>> validRanges;

    public StatsRange(String name, List<Pair<Integer, Integer>> validRanges) {
        this.name = name;
        this.validRanges = validRanges;
    }

    public String getName() {
        return name;
    }

    public boolean isValidValue(int val) {
        for (var range : validRanges) {
            int min = range.getLeft();
            int max = range.getRight();
            if ((val >= min) && (val <= max)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        var pairsStr = validRanges.stream()
                .map(pair -> "(" + pair.getLeft() + "-" + pair.getRight() + ")")
                .collect(Collectors.joining(", "));
        return "[" + name + ": " + pairsStr + "]";
    }

    public static StatsRange fromString(String line) {
        var parts = StringUtils.splitByWholeSeparator(line, ": ", 2);
        String name = parts[0];
        var rangeStrs = StringUtils.splitByWholeSeparator(parts[1], " or ");
        var ranges = Arrays.stream(rangeStrs)
                .map(str -> {
                    var rangeParts = StringUtils.split(str, '-');
                    int min = Integer.parseInt(rangeParts[0]);
                    int max = Integer.parseInt(rangeParts[1]);
                    return Pair.of(min, max);
                }).collect(Collectors.toList());
        return new StatsRange(name, ranges);
    }
}
