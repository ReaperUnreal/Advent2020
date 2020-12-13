package com.guillaumecl.puzzle12;

public final class Step {
    private final Instruction instr;
    private final int amount;

    private Step(Instruction instr, int amount) {
        this.instr = instr;
        this.amount = amount;
    }

    public Instruction getInstr() {
        return instr;
    }

    public int getAmount() {
        return amount;
    }

    public static Step fromString(String str) {
        char c = str.charAt(0);
        int num = Integer.parseUnsignedInt(str.substring(1));
        return new Step(Instruction.fromChar(c), num);
    }
}
