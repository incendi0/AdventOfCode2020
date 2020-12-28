package xyz.qzem.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Day22 {

    private static final String fileName =
            Objects.requireNonNull(Day05.class.getClassLoader().getResource("input/day22.txt")).getPath();

    public static void main(String[] args) throws IOException {
        List<String> xs = Files.lines(Paths.get(fileName)).collect(Collectors.toList());
        solveFirst(xs);
        solveSecond(xs);
    }

    private static void solveSecond(List<String> xs) {
        Deque<Integer> as = new ArrayDeque<>();
        Deque<Integer> bs = new ArrayDeque<>();
        parseLines(xs, as, bs);
        String ret = solveSecondRec(as, bs);
        if (ret.equals("A")) {
            System.out.println(calculate(as));
        } else {
            System.out.println(calculate(bs));
        }
    }

    private static String solveSecondRec(Deque<Integer> as, Deque<Integer> bs) {
        Set<String> seen = new HashSet<>();
        while (!as.isEmpty() && !bs.isEmpty()) {
            String s = serialize(as);
            if (!seen.add(s)) {
                return "A";
            }

            int a = as.pollFirst(), b = bs.pollFirst();
            if (as.size() >= a && bs.size() >= b) {
                Deque<Integer> ac = getDequeCopyOfN(as, a);
                Deque<Integer> bc = getDequeCopyOfN(bs, b);
                String ret = solveSecondRec(ac, bc);
                if (ret.equals("A")) {
                    as.offerLast(a);
                    as.offerLast(b);
                } else {
                    bs.offerLast(b);
                    bs.offerLast(a);
                }
            } else {
                if (a > b) {
                    as.offerLast(a);
                    as.offerLast(b);
                } else {
                    bs.offerLast(b);
                    bs.offerLast(a);
                }
            }
        }
        if (as.isEmpty()) {
            return "B";
        } else {
            return "A";
        }
    }

    private static Deque<Integer> getDequeCopyOfN(Deque<Integer> as, int a) {
        Deque<Integer> ac = new ArrayDeque<>();
        int idx = 0;
        for (int n : as) {
            if (idx == a) {
                break;
            }
            ac.offer(n);
            idx++;
        }
        return ac;
    }

    private static String serialize(Deque<Integer> xs) {
        StringBuilder sb = new StringBuilder();
        xs.forEach(x -> sb.append(x).append(","));
        return sb.toString();
    }

    private static void solveFirst(List<String> xs) {
        Deque<Integer> as = new ArrayDeque<>();
        Deque<Integer> bs = new ArrayDeque<>();
        parseLines(xs, as, bs);

        while (!as.isEmpty() && !bs.isEmpty()) {
            int a = as.pollFirst(), b = bs.pollFirst();
            if (a > b) {
                as.offerLast(a);
                as.offerLast(b);
            } else if (a < b) {
                bs.offerLast(b);
                bs.offerLast(a);
            }
        }

        if (as.isEmpty()) {
            System.out.println(calculate(bs));
        } else {
            System.out.println(calculate(as));
        }
    }

    private static void parseLines(List<String> xs, Deque<Integer> as, Deque<Integer> bs) {
        boolean first = true;
        for (String x : xs) {
            if (x.isEmpty()) {
                first = false;
                continue;
            }
            if (first) {
                collect(as, x);
            } else {
                collect(bs, x);
            }
        }
    }

    private static int calculate(Deque<Integer> dq) {
        int idx = 1, ret = 0;
        while (!dq.isEmpty()) {
            ret += dq.pollLast() * idx;
            idx++;
        }
        return ret;
    }

    private static void collect(Deque<Integer> xs, String x) {
        if (x.startsWith("P")) {
            return;
        }
        xs.offerLast(Integer.parseInt(x));
    }
}
