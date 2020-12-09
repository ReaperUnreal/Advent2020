package com.guillaumecl.puzzle8;

import org.apache.commons.lang3.StringUtils;

public enum InstructionType {
    NOP,
    ACC,
    JMP;

    public static InstructionType fromString(String str) throws InvalidInstructionException {
        if (StringUtils.equalsIgnoreCase(str, "nop")) {
            return NOP;
        } else if (StringUtils.equalsIgnoreCase(str, "acc")) {
            return ACC;
        } else if (StringUtils.equalsIgnoreCase(str, "jmp")) {
            return JMP;
        } else {
            throw new InvalidInstructionException(str);
        }
    }
}
