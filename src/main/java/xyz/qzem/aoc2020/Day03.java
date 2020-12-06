package xyz.qzem.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day03 {

    private static final String fileName =
            Objects.requireNonNull(Day05.class.getClassLoader().getResource("input/day03.txt")).getPath();

    public static void main(String[] args) throws IOException {
        List<String> xs = Files.lines(Paths.get(fileName)).collect(Collectors.toList());
        solveFirst(xs);
        solveSecond(xs);
    }

    private static void solveFirst(List<String> xs) {
        int n = xs.size(), m = xs.get(0).length();
        int x = 0, y = 0;
        int count = 0;
        for (int i = 1; i < n; i++) {
            x += 3;
            y += 1;
            int idx = x % m;
            if (xs.get(y).charAt(idx) == '#') {
                count++;
            }
        }
        System.out.println(count);
    }

    private static void solveSecond(List<String> xs) {
        int n = xs.size(), m = xs.get(0).length();
        int[][] slopes = new int[][] {
                {1, 1}, {3, 1}, {5, 1}, {7, 1}, {1, 2}
        };
        int[] counts = new int[slopes.length];
        for (int i = 0; i < slopes.length; i++) {
            int[] slope = slopes[i];
            int dx = slope[0], dy = slope[1];
            int x = 0, y = 0;
            int count = 0;
            while ((y += dy) < n) {
                x += dx;
                int idx = x % m;
                if (xs.get(y).charAt(idx) == '#') {
                    count++;
                }
            }
            counts[i] = count;
        }
        System.out.println(Arrays.stream(counts).asLongStream().reduce(1, (a, acc) -> a * acc));
    }
}

