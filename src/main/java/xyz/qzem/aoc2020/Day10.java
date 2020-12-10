package xyz.qzem.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day10 {

    private static final String fileName = "D:\\code\\workspace\\aoc2020\\src\\main\\resources\\input\\day10.txt";

    public static void main(String[] args) throws IOException {
        List<Integer> xs = Files.lines(Paths.get(fileName)).map(Integer::parseInt).collect(Collectors.toList());
        solveFirst(xs);
        solveSecond(xs);
    }

    private static void solveFirst(List<Integer> xs) {
        xs.sort(Comparator.naturalOrder());
        int c1 = 0, c3 = 0;
        int prev = 0;
        for (Integer x : xs) {
            long d = x - prev;
            if (d == 1) {
                c1++;
            } else if (d == 3) {
                c3++;
            }
            prev = x;
        }
        System.out.println(c1 * (c3 + 1));
    }

    private static void solveSecond(List<Integer> xs) {
        int n = xs.size();
        xs.sort(Comparator.naturalOrder());
        Map<Integer, Long> f = new HashMap<>();
        f.put(0, 1L);
        for (int i = 1; i <= n; i++) {
            int curr = xs.get(i - 1);
            for (int j = 0; j < i; j++) {
                int prev = j == 0 ? 0 : xs.get(j - 1);
                if (curr - prev <= 3) {
                    if (!f.containsKey(curr)) {
                        f.put(curr, 0L);
                    }
                    f.put(curr, f.get(curr) +f.get(prev));
                }
            }
        }
        System.out.println(f.get(xs.get(xs.size() - 1)));
    }
}
