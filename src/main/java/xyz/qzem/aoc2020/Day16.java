package xyz.qzem.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day16 {

    private static final String fileName =
            Objects.requireNonNull(Day05.class.getClassLoader().getResource("input/day16.txt")).getPath();
    private static final Pattern pattern = Pattern.compile("(\\d+)-(\\d+).*\\s+(\\d+)-(\\d+)");
    private static final Pattern pattern2 = Pattern.compile("(.*):\\s+(\\d+)-(\\d+).*\\s+(\\d+)-(\\d+)");
    private static final String ticket = "73,53,173,107,113,89,59,167,137,139,71,179,131,181,67,83,109,127,61,79";
    private static final int[] discards = new int[] {25, 29, 32, 41, 48, 51, 55, 70, 74, 79, 80, 89, 91, 94, 95, 98, 104, 106, 107, 119, 122, 125, 126, 138, 147, 155, 167, 170, 175, 183, 188, 189, 191, 199, 212, 218, 219, 223, 224, 241, 243, 247, 249, 250, 253, 258};

    public static void main(String[] args) throws IOException {
        List<String> xs = Files.lines(Paths.get(fileName)).collect(Collectors.toList());
        solveFirst(xs);
        solveSecond(xs);
    }


    private static void solveSecond(List<String> xs) {
        Map<String, List<int[]>> ranges = new HashMap<>();
        boolean flag = false;
        Map<Integer, Set<String>> map = new HashMap<>();
        for (int i = 0; i < xs.size(); i++) {
            String x = xs.get(i);
            boolean dis = false;
            for (int d : discards) {
                if (d == i) {
                    dis = true;
                    break;
                }
            }
            if (dis) {
                continue;
            }
            if (!flag) {
                Matcher m = pattern2.matcher(x);
                if (m.find()) {
                    List<int[]> vs = new ArrayList<>();
                    vs.add(new int[]{Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3))});
                    vs.add(new int[]{Integer.parseInt(m.group(4)), Integer.parseInt(m.group(5))});
                    ranges.put(m.group(1), vs);
                }
                if (x.startsWith("nearby tickets:")) {
                    flag = true;
                    for (int j = 0; j < 20; j++) {
                        map.put(j, new HashSet<>(ranges.keySet()));
                    }
                }
            } else {
                List<Integer> arr = Arrays.stream(x.split(",")).map(Integer::parseInt).collect(Collectors.toList());
                for (int j = 0; j < 20; j++) {
                    for (Map.Entry<String, List<int[]>> range : ranges.entrySet()) {
                        if (!contains(range.getValue(), arr.get(j))) {
                            map.get(j).remove(range.getKey());
                        }
                    }
                }
            }
        }
        System.out.println(map);
        Set<String> determined = new HashSet<>();
        while (determined.size() < 20) {
            for (Map.Entry<Integer, Set<String>> entry : map.entrySet()) {
                if (entry.getValue().size() == 1) {
                    determined.add(entry.getValue().iterator().next());
                } else {
                    Set<String> set = entry.getValue();
                    set.removeIf(determined::contains);
                }
            }
        }
        List<Integer> tickets = Arrays.stream(ticket.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        long ret = 1;
        for (Map.Entry<Integer, Set<String>> entry : map.entrySet()) {
            if (entry.getValue().iterator().next().startsWith("departure")) {
                ret *= tickets.get(entry.getKey());
            }
        }
        System.out.println(ret);
    }
    private static boolean contains(List<int[]> rs, int v) {
        for (int[] r : rs) {
            if (r[0] <= v && v <= r[1]) {
                return true;
            }
        }
        return false;
    }

    private static void solveFirst(List<String> xs) {
        List<int[]> ranges = new ArrayList<>();
        boolean flag = false;
        int total = 0;
        List<Integer> invalid = new ArrayList<>();
        for (int i = 0; i < xs.size(); i++) {
            String x = xs.get(i);
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
                        invalid.add(i);
                    }
                }
            }
        }
        System.out.println(invalid);
        System.out.println(total);
    }
}
