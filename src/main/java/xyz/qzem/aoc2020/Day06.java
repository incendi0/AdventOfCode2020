package xyz.qzem.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Day06 {

    private static final String fileName =
            Objects.requireNonNull(Day05.class.getClassLoader().getResource("input/day06.txt")).getPath();

    public static void main(String[] args) throws IOException {
        List<String> xs = Files.lines(Paths.get(fileName)).collect(Collectors.toList());
        xs.add("");
        solveFirst(xs);
        solveSecond(xs);
    }

    private static void solveFirst(List<String> xs) {
        int count = 0;
        Set<Character> yes = new HashSet<>();
        for (String line : xs) {
            if (line.trim().isEmpty()) {
                count += yes.size();
                yes.clear();
            } else {
                for (int i = 0; i < line.length(); i++) {
                    yes.add(line.charAt(i));
                }
            }
        }
        System.out.println(count);
    }

    private static void solveSecond(List<String> xs) {
        int count = 0;
        Set<Character> yesToAll = new HashSet<>();
        boolean isFirst = false;
        for (String line : xs) {
            if (line.trim().isEmpty()) {
                count += yesToAll.size();
                yesToAll.clear();
                isFirst = false;
            } else {
                if (!isFirst) {
                    isFirst = true;
                    for (int i = 0; i < line.length(); i++) {
                        yesToAll.add(line.charAt(i));
                    }
                } else {
                    Set<Character> yesToOne = new HashSet<>();
                    for (int i = 0; i < line.length(); i++) {
                        yesToOne.add(line.charAt(i));
                    }
                    yesToAll.removeIf(k -> !yesToOne.contains(k));
                }
            }
        }
        System.out.println(count);
    }
}
