package com.guillaumecl.puzzle8;

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

    private List<String> splitLines(String input) {
        return Arrays.stream(StringUtils.split(input, '\n'))
                .map(String::trim)
                .filter(s -> !StringUtils.isBlank(s))
                .collect(Collectors.toList());
    }

    void part1() throws IOException {
        var lines = readLines();
        /*
        var lines = splitLines("nop +0\n" +
                "acc +1\n" +
                "jmp +4\n" +
                "acc +3\n" +
                "jmp -3\n" +
                "acc -99\n" +
                "acc +1\n" +
                "jmp -4\n" +
                "acc +6");
         */
        var program = lines.stream()
                .map(Instruction::fromString)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
        var computer = new ComputerState(program);
        computer.run();
        System.out.println(computer.getStateStr());
    }

    void part2() throws IOException {
        var lines = readLines();
        /*
        var lines = splitLines("nop +0\n" +
                "acc +1\n" +
                "jmp +4\n" +
                "acc +3\n" +
                "jmp -3\n" +
                "acc -99\n" +
                "acc +1\n" +
                "jmp -4\n" +
                "acc +6");
         */
        var program = lines.stream()
                .map(Instruction::fromString)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
        for (int idx = 0; idx < program.size(); idx++) {
            var instr = program.get(idx);
            boolean isJmp = instr.getType() == InstructionType.JMP;
            boolean isNop = instr.getType() == InstructionType.NOP;
            if (isJmp) {
                program.set(idx, new Instruction(InstructionType.NOP, instr.getOffset()));
            } else if (isNop) {
                program.set(idx, new Instruction(InstructionType.JMP, instr.getOffset()));
            }
            var computer = new ComputerState(program);
            boolean wasSuccessful = computer.run();
            if (wasSuccessful) {
                System.out.println(computer.getStateStr());
                break;
            }
            if (isJmp) {
                program.set(idx, instr);
            } else if (isNop) {
                program.set(idx, instr);
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
