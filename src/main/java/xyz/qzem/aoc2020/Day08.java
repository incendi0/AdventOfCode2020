package xyz.qzem.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day08 {

    private static final String fileName = "D:\\code\\workspace\\aoc2020\\src\\main\\resources\\input\\day08.txt";

    private List<Instruction> instructions;
    private int idx;
    private int accumulator;

    public Day08(List<String> instructions) {
        this.instructions = parseInstructions(instructions);
        this.idx = 0;
        this.accumulator = 0;
    }

    public static void main(String[] args) throws IOException {
        List<String> xs = Files.lines(Paths.get(fileName)).collect(Collectors.toList());
        new Day08(xs).solveFirst();
        new Day08(xs).solveSecond();
    }

    private void solveFirst() {
        Set<Integer> visited = new HashSet<>();
        while (true) {
            if (visited.contains(idx)) {
                System.out.println(accumulator);
                break;
            }
            visited.add(idx);
            execute();
        }
    }

    private void solveSecond() {
        idx = 0;
        for (Instruction ins : instructions) {
            if (ins.act.equals("nop") || ins.act.equals("jmp")) {
                changeInsAct(ins);
                if (canTerminate()) {
                    break;
                }
                changeInsAct(ins);
            }
        }
    }

    private List<Instruction> parseInstructions(List<String> xs) {
        List<Instruction> ret = new ArrayList<>();
        for (String x : xs) {
            String[] ss = x.split(" ");
            ret.add(new Instruction(ss[0], Integer.parseInt(ss[1])));
        }
        return ret;
    }

    private boolean canTerminate() {
        Set<Integer> visited = new HashSet<>();
        idx = 0;
        accumulator = 0;
        while (idx < instructions.size()) {
            if (visited.contains(idx)) {
                return false;
            }
            visited.add(idx);
            execute();
        }
        System.out.println(accumulator);
        return true;
    }

    private void changeInsAct(Instruction ins) {
        if (ins.act.equals("nop")) {
            ins.act = "jmp";
        } else if (ins.act.equals("jmp")) {
            ins.act = "nop";
        }
    }

    private void execute() {
        Instruction ins = instructions.get(idx);
        if (ins.act.equals("nop")) {
            idx++;
        } else if (ins.act.equals("acc")) {
            accumulator += ins.value;
            idx++;
        } else if (ins.act.equals("jmp")) {
            idx += ins.value;
        }
    }

    private static class Instruction {
        String act;
        int value;

        public Instruction(String act, int value) {
            this.act = act;
            this.value = value;
        }
    }

}
