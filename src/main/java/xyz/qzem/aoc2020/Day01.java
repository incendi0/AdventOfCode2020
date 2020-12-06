package xyz.qzem.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Day01 {

    private static final int target = 2020;
    private static final String fileName =
            Objects.requireNonNull(Day05.class.getClassLoader().getResource("input/day01.txt")).getPath();

    public static void main(String[] args) throws IOException {
        int[] xs = Files.lines(Paths.get(fileName)).mapToInt(Integer::parseInt).toArray();
        solveFirst(xs);
        solveSecond(xs);
    }

    private static void solveFirst(int[] xs) {
        Set<Integer> seen = new HashSet<>();
        for (int x : xs) {
            if (seen.contains(target - x)) {
                System.out.println(x * (target - x));
                break;
            } else {
                seen.add(x);
            }
        }
    }

    private static void solveSecond(int[] xs) {
        int n = xs.length;
        OUT:
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (xs[i] + xs[j] + xs[k] == target) {
                        System.out.println(xs[i] * xs[j] * xs[k]);
                        break OUT;
                    }
                }
            }
        }
    }
}
