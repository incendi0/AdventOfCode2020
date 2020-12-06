package xyz.qzem.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day05 {

    private static final String fileName =
            Objects.requireNonNull(Day05.class.getClassLoader().getResource("input/day05.txt")).getPath();

    public static void main(String[] args) throws IOException {
        List<String> xs = Files.lines(Paths.get(fileName)).collect(Collectors.toList());
        solveFirst(xs);
        solveSecond(xs);
    }

    private static void solveFirst(List<String> xs) {
        int id = 0;
        for (String x : xs) {
            String row = x.substring(0, 7).replace('F', '0').replace('B', '1');
            String col = x.substring(7).replace('R', '1').replace('L', '0');
            int t = Integer.parseInt(row, 2) * 8 + Integer.parseInt(col, 2);
            id = Math.max(id, t);
        }
        System.out.println(id);
    }

    private static void solveSecond(List<String> xs) {
        List<Integer> seats = new ArrayList<>();
        for (String x : xs) {
            String row = x.substring(0, 7).replace('F', '0').replace('B', '1');
            String col = x.substring(7).replace('R', '1').replace('L', '0');
            int t = Integer.parseInt(row, 2) * 8 + Integer.parseInt(col, 2);
            seats.add(t);
        }
        seats.sort(Comparator.naturalOrder());
        for (int i = 0; i < seats.size() - 1; i++) {
            if (seats.get(i) + 1 != seats.get(i + 1)) {
                System.out.println(seats.get(i) + 1);
                break;
            }
        }
    }
}
