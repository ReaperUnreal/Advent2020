package com.guillaumecl.puzzle12;

import com.guillaumecl.puzzle12.part1.Boat;
import com.guillaumecl.puzzle12.part1.Step;
import com.guillaumecl.puzzle12.part2.DumbBoat;
import com.guillaumecl.puzzle12.part2.Waypoint;
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

    void part1() throws IOException {
        var lines = readLines();
        /*
        var lines = splitLines("F10\n" +
                "N3\n" +
                "F7\n" +
                "R90\n" +
                "F11");
         */
        var boat = new Boat();
        lines.stream()
                .map(Step::fromString)
                .forEach(boat::applyStep);

        System.out.println(boat);
        System.out.println("Total: " + (Math.abs(boat.getX()) + Math.abs(boat.getY())));
    }

    private void rotateWaypointClockwise(DumbBoat b, Waypoint w) {
        int diffX = w.getX() - b.getX();
        int diffY = w.getY() - b.getY();
        int x = diffY;
        int y = -diffX;
        w.setX(b.getX() + x);
        w.setY(b.getY() + y);
    }

    private void rotateWaypointClockwiseBy(int deg, DumbBoat b, Waypoint w) {
        if (deg % 90 != 0) {
            System.err.println("Cannot rotate by " + deg + ", not a multiple of 90.");
            return;
        }
        while (deg >= 360) {
            deg -= 360;
        }
        while (deg < 0) {
            deg += 360;
        }
        while (deg > 0) {
            rotateWaypointClockwise(b, w);
            deg -= 90;
        }
    }

    void part2() throws IOException {
        var lines = readLines();
        /*
        var lines = splitLines("F10\n" +
                "N3\n" +
                "F7\n" +
                "R90\n" +
                "F11");
         */
        var steps = lines.stream()
                .map(Step::fromString)
                .collect(Collectors.toList());
        var w = new Waypoint();
        w.setX(10);
        w.setY(1);
        var b = new DumbBoat();
        for (var step : steps) {
            var instr = step.getInstr();
            var amount = step.getAmount();
            switch (instr) {
                case EAST:
                    w.setX(w.getX() + amount);
                    break;
                case WEST:
                    w.setX(w.getX() - amount);
                    break;
                case NORTH:
                    w.setY(w.getY() + amount);
                    break;
                case SOUTH:
                    w.setY(w.getY() - amount);
                    break;
                case FORWARD:
                    int diffX = w.getX() - b.getX();
                    int diffY = w.getY() - b.getY();
                    b.setX(b.getX() + diffX * amount);
                    b.setY(b.getY() + diffY * amount);
                    w.setX(b.getX() + diffX);
                    w.setY(b.getY() + diffY);
                    break;
                case LEFT:
                    rotateWaypointClockwiseBy(-amount, b, w);
                    break;
                case RIGHT:
                    rotateWaypointClockwiseBy(amount, b, w);
                    break;
                default:
                    System.err.println("Unknown instruction: " + instr);
                    break;
            }
        }

        System.out.println("Boat: " + b + " Waypoint: " + w);
        System.out.println("Total: " + (Math.abs(b.getX()) + Math.abs(b.getY())));
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
