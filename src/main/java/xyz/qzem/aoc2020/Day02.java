package xyz.qzem.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day02 {

    private static final String fileName =
            Objects.requireNonNull(Day05.class.getClassLoader().getResource("input/day02.txt")).getPath();

    public static void main(String[] args) throws IOException {
        List<String> xs = Files.lines(Paths.get(fileName)).collect(Collectors.toList());
        solveFirst(xs);
        solveSecond(xs);
    }

    private static void solveFirst(List<String> xs) {
        int count = 0;
        for (String x : xs) {
            String[] ss = x.split(" ");
            String[] ns = ss[0].split("-");
            int l = Integer.parseInt(ns[0]), r = Integer.parseInt(ns[1]);
            char ch = ss[1].charAt(0);
            int cc = 0;
            for (int j = 0; j < ss[2].length(); j++) {
                if (ss[2].charAt(j) == ch) {
                    cc++;
                }
            }
            if (cc >= l && cc <= r) {
                count++;
            }
        }
        System.out.println(count);
    }

    private static void solveSecond(List<String> xs) {
        int count = 0;
        for (String x : xs) {
            String[] ss = x.split(" ");
            String[] ns = ss[0].split("-");
            int l = Integer.parseInt(ns[0]), r = Integer.parseInt(ns[1]);
            char ch = ss[1].charAt(0);
            boolean flag = true, appeared = false;
            for (int j = 0; j < ss[2].length(); j++) {
                if (j == l - 1 || j == r - 1) {
                    if (ss[2].charAt(j) == ch) {
                        if (!appeared) {
                            appeared = true;
                        } else {
                            flag = false;
                            break;
                        }
                    }
                }
            }
            if (flag && appeared) {
                count++;
            }
        }
        System.out.println(count);
    }
}
