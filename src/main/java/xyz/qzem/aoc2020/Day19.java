package xyz.qzem.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Day19 {

    private static final String fileName =
            Objects.requireNonNull(Day05.class.getClassLoader().getResource("input/day19.txt")).getPath();

    public static void main(String[] args) throws IOException {
        List<String> xs = Files.lines(Paths.get(fileName)).collect(Collectors.toList());
        solveFirst(xs);
//        solveSecond(xs);
    }

    private static void solveFirst(List<String> xs) {
        boolean separate = false;
        Map<String, List<List<String>>> graph = new HashMap<>();
        int count = 0;
        for (String x : xs) {
            if (x.isEmpty()) {
                separate = true;
                continue;
            }
            if (!separate) {
                String[] kvs = x.split(":");
                String k = kvs[0];
                if (!graph.containsKey(k)) {
                    graph.put(k, new ArrayList<>());
                }
                String[] vs = kvs[1].split("\\|");
                for (String v : vs) {
                    List<String> ss = new ArrayList<>();
                    v = v.trim().replace("\"", "");
                    if (Character.isLetter(v.charAt(0))) {
                        ss.add(v);
                    } else {
                        ss.addAll(Arrays.asList(v.split(" ")));
                    }
                    graph.get(k).add(ss);
                }
            } else {
                int[] m = match(x, 0, graph, "0");
                if (m[0] == 1 && m[1] == x.length()) {
                    count++;
                }
            }
        }
        System.out.println(count);
    }

    private static int[] match(String s, int sx, Map<String, List<List<String>>> graph, String rx) {
        List<List<String>> rules = graph.get(rx);
        if (rules.get(0).get(0).equals("a") || rules.get(0).get(0).equals("b")) {
            char v = rules.get(0).get(0).charAt(0);
            if (s.charAt(sx) == v) {
                return new int[] {1, sx + 1};
            } else {
                return new int[] {0, -1};
            }
        } else {
            for (List<String> rule : rules) {
                int curr = sx;
                for (String r : rule) {
                    int[] ret = match(s, curr, graph, r);
                    if (ret[0] != 1) {
                        curr = -1;
                        break;
                    }
                    curr = ret[1];
                }
                if (curr != -1) {
                    return new int[] {1, curr};
                }
            }
        }
        return new int[] {0, -1};
    }

}
