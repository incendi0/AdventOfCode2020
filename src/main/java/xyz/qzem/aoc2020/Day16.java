package xyz.qzem.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day16 {

    private static final String fileName = "D:\\code\\workspace\\aoc2020\\src\\main\\resources\\input\\day16.txt";
    private static final Pattern pattern = Pattern.compile("(\\d+)-(\\d+).*\\s+(\\d+)-(\\d+)");
    private static final Pattern pattern2 = Pattern.compile("(.*):(\\d+)-(\\d+).*\\s+(\\d+)-(\\d+)");

    public static void main(String[] args) throws IOException {
        List<String> xs = Files.lines(Paths.get(fileName)).collect(Collectors.toList());
        solveFirst(xs);
//        solveSecond(xs);
    }


    private static void solveSecond(List<String> xs) {
        Map<String, List<int[]>> ranges = new HashMap<>();
        boolean flag = false;
        for (String x : xs) {
            if (!flag) {
                Matcher m = pattern.matcher(x);
                if (m.find()) {
                    ranges.add(new int[]{Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2))});
                    ranges.add(new int[]{Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4))});
                }
                if (x.startsWith("nearby tickets:")) {
                    flag = true;
                }
            } else {
                List<Integer> arr = Arrays.stream(x.split(",")).map(Integer::parseInt).collect(Collectors.toList());
                for (int a : arr) {
                    boolean fit = false;
                    for (int[] range : ranges) {
                        if (range[0] <= a && range[1] >= a) {
                            fit = true;
                            break;
                        }
                    }
                    if (!fit) {
                        total += a;
                    }
                }
            }
        }
    }

    private static void solveFirst(List<String> xs) {
        List<int[]> ranges = new ArrayList<>();
        boolean flag = false;
        int total = 0;
        for (String x : xs) {
            if (!flag) {
                Matcher m = pattern.matcher(x);
                if (m.find()) {
                    ranges.add(new int[]{Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2))});
                    ranges.add(new int[]{Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4))});
                }
                if (x.startsWith("nearby tickets:")) {
                    flag = true;
                }
            } else {
                List<Integer> arr = Arrays.stream(x.split(",")).map(Integer::parseInt).collect(Collectors.toList());
                for (int a : arr) {
                    boolean fit = false;
                    for (int[] range : ranges) {
                        if (range[0] <= a && range[1] >= a) {
                            fit = true;
                            break;
                        }
                    }
                    if (!fit) {
                        total += a;
                    }
                }
            }
        }
        System.out.println(total);
    }
}
