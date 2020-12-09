package com.guillaumecl.puzzle8;

public class InvalidInstructionException extends Exception {
    public InvalidInstructionException(String instr) {
        super("Invalid instruction: \"" + instr + "\"");
    }
}
