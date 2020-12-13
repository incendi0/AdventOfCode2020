package xyz.qzem.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day13 {

    private static final String fileName =
            Objects.requireNonNull(Day05.class.getClassLoader().getResource("input/day13.txt")).getPath();

    public static void main(String[] args) throws IOException {
        List<String> xs = Files.lines(Paths.get(fileName)).collect(Collectors.toList());
        int curr = Integer.parseInt(xs.get(0));
        List<Integer> avails = Arrays.stream(xs.get(1).split(",")).filter(x -> !x.endsWith("x")).map(Integer::parseInt).collect(Collectors.toList());
        solveFirst(curr, avails);
        solveSecond(xs.get(1));
    }

    private static void solveFirst(int curr, List<Integer> avails) {
        int t = curr;
        while (true) {
            for (int d : avails) {
                if (curr % d == 0) {
                    System.out.println(d * (curr - t));
                    return;
                }
            }
            curr++;
        }
    }

    private static void solveSecond(String xs) {
        List<int[]> buses = new ArrayList<>();
        String[] ss = xs.split(",");
        for (int i = 0; i < ss.length; i++) {
            if (!ss[i].equals("x")) {
                buses.add(new int[] {Integer.parseInt(ss[i]), i});
            }
        }

        long start = 0, step = buses.get(0)[0];
        for (int i = 1; i < buses.size(); i++) {
            int x = 0, offset = buses.get(i)[1], bus = buses.get(i)[0];
            while ((start + step * x + offset) % bus != 0) {
                x++;
            }

            start += x * step;
            step *= bus;
        }
        System.out.println(start);
    }
}
