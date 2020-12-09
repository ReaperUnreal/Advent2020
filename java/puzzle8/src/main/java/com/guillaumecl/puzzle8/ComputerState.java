package com.guillaumecl.puzzle8;

import java.util.List;
import java.util.stream.Collectors;

public final class ComputerState {
    private final List<Instruction> program;
    private int ip;
    private int acc;

    public ComputerState(List<Instruction> program) {
        this.program = program;
        ip = 0;
        acc = 0;
    }

    public int getIp() {
        return ip;
    }

    public void setIp(int ip) {
        this.ip = ip;
    }

    public void incIp() {
        ip++;
    }

    public int getAcc() {
        return acc;
    }

    public void setAcc(int acc) {
        this.acc = acc;
    }

    private void resetState() {
        ip = 0;
        acc = 0;
        program.forEach(Instruction::reset);
    }

    public boolean run() {
        resetState();
        while (true) {
            if (ip == program.size()) {
                return true;
            } else if (ip > program.size()) {
                return false;
            }
            var instr = program.get(ip);
            if (instr.isHasExecuted()) {
                return false;
            }
            instr.execute(this);
        }
    }

    public String getStateStr() {
        return "[IP: " + ip + ", ACC: " + acc + "]";
    }

    @Override
    public String toString() {
        return getStateStr() + "\n"
                + program.stream()
                .map(Instruction::toString)
                .collect(Collectors.joining("\n"));
    }
}
