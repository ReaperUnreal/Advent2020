package com.guillaumecl.puzzle8;

import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

public final class Instruction {
    private final InstructionType type;
    private final int offset;

    public Instruction(InstructionType type, int offset) {
        this.type = type;
        this.offset = offset;
    }

    public InstructionType getType() {
        return type;
    }

    public int getOffset() {
        return offset;
    }

    public void execute(ComputerState state) {
        switch (type) {
            case ACC:
                state.setAcc(state.getAcc() + offset);
                state.incIp();
                break;
            case JMP:
                state.setIp(state.getIp() + offset);
                break;
            case NOP:
                state.incIp();
                break;
            default:
                System.err.println("Unknown instruction: " + this);
                break;
        }
    }

    @Override
    public String toString() {
        return type.toString() + " " + offset;
    }

    public static Optional<Instruction> fromString(String str) {
        var parts = StringUtils.split(str, " ", 2);
        try {
            var type = InstructionType.fromString(StringUtils.trim(parts[0]));
            var offset = Integer.parseInt(StringUtils.trim(parts[1]));
            return Optional.of(new Instruction(type, offset));
        } catch (InvalidInstructionException | NumberFormatException e) {
            System.err.println(e);
            return Optional.empty();
        }
    }
}
