package xyz.qzem.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Day09 {

    private static final String fileName =
            Objects.requireNonNull(Day05.class.getClassLoader().getResource("input/day09.txt")).getPath();
    private static final int N = 25;

    public static void main(String[] args) throws IOException {
        List<Long> xs = Files.lines(Paths.get(fileName)).map(Long::parseLong).collect(Collectors.toList());
        solveFirst(xs);
        solveSecond(xs, 511, 18272118);
    }

    private static void solveFirst(List<Long> xs) {
        Map<Long, Integer> seen = new HashMap<>();
        for (int i = 0; i < xs.size(); i++) {
            if (i >= N) {
                long target = xs.get(i);
                boolean flag= false;
                for (int j = 0; j < N; j++) {
                    long d = xs.get(i - j - 1);
                    if (seen.containsKey(target - d) && seen.get(target - d) != i - j - 1) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    System.out.println(i);
                    System.out.println(target);
                    break;
                }
                seen.remove(xs.get(i - N));
            }
            seen.put(xs.get(i), i);
        }
    }

    private static void solveSecond(List<Long> xs, int targetIndex, long target) {
        Map<Long, Integer> prefixSum = new HashMap<>();
        long ps = 0;
        for (int i = 0; i < targetIndex; i++) {
            ps += xs.get(i);
            if (prefixSum.containsKey(ps - target)) {
                int from = prefixSum.get(ps - target), to = i;
                List<Long> ret = xs.subList(from, to + 1);
                Collections.sort(ret);
                System.out.println(ret.get(0) + ret.get(ret.size() - 1));
                break;
            }
            prefixSum.put(ps, i);
        }
    }

}
