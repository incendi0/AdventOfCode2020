package xyz.qzem.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Day07 {

    private static final String fileName =
            Objects.requireNonNull(Day05.class.getClassLoader().getResource("input/day07.txt")).getPath();
    private static final String target = "shiny gold";
    private static final String end = "no other";

    public static void main(String[] args) throws IOException {
        List<String> xs = Files.lines(Paths.get(fileName)).collect(Collectors.toList());
        solveFirst(xs);
        solveSecond(xs);
    }

    private static int secondCount;

    private static void solveSecond(List<String> xs) {
        Map<String, List<Node>> graph = new HashMap<>();
        List<String> starts = new ArrayList<>();
        for (String line : xs) {
            Container c = extractColors(line);
            starts.add(c.color);
            if (!graph.containsKey(c.color)) {
                graph.put(c.color, new ArrayList<>());
            }
            graph.get(c.color).addAll(c.nodes);
        }
        String start = "shiny gold";
        secondCount = 0;
        dfs(1, start, end, graph);
        System.out.println(secondCount);
    }

    private static void solveFirst(List<String> xs) {
        Map<String, List<Node>> graph = new HashMap<>();
        List<String> starts = new ArrayList<>();
        for (String line : xs) {
            Container c = extractColors(line);
            starts.add(c.color);
            if (!graph.containsKey(c.color)) {
                graph.put(c.color, new ArrayList<>());
            }
            graph.get(c.color).addAll(c.nodes);
        }
        int count = 0;
        for (String s : starts) {
            if (!target.equals(s) && bfs(graph, s, target)) {
                count++;
            }
        }
        System.out.println(count);
    }

    private static void dfs(int c, String start, String end, Map<String, List<Node>> graph) {
        if (!graph.containsKey(start)) {
            return;
        }
        for (Node v : graph.get(start)) {
            if (v.color.equals(end)) {
                continue;
            }
            int nc = c * v.count;
            secondCount += nc;
            dfs(nc, v.color, end, graph);
        }
    }

    private static boolean bfs(Map<String, List<Node>> graph, String start, String target) {
        Set<String> visited = new HashSet<>();
        Queue<String> q = new LinkedList<>();
        q.offer(start);
        visited.add(start);
        while (!q.isEmpty()) {
            String u = q.poll();
            if (target.equals(u)) {
                return true;
            }
            if (!graph.containsKey(u)) {
                continue;
            }
            for (Node v : graph.get(u)) {
                if (!visited.contains(v.color)) {
                    q.offer(v.color);
                    visited.add(v.color);
                }
            }
        }
        return false;
    }

    private static Container extractColors(String line) {
        String[] ss = line.split(" contain ");
        String u = extract(ss[0]).color;
        String[] vs = ss[1].split(", ");
        List<Node> nodes = new ArrayList<>();
        for (String v : vs) {
            nodes.add(extract(v));
        }
        return new Container(u, nodes);
    }

    private static Node extract(String s) {
        int d = s.indexOf(" bag");
        int f = 0;
        int count = 0;
        while (f < d && Character.isDigit(s.charAt(f))) {
            count = count * 10 + s.charAt(f) - '0';
            f++;
        }
        while (f < d && !Character.isLetter(s.charAt(f))) {
            f++;
        }
        if (count == 0) {
            count = 1;
        }
        return new Node(count, s.substring(f, d));
    }

    private static class Node {
        int count;
        String color;

        public Node(int count, String color) {
            this.count = count;
            this.color = color;
        }
    }

    private static class Container {
        String color;
        List<Node> nodes;

        public Container(String color, List<Node> nodes) {
            this.color = color;
            this.nodes = nodes;
        }
    }
}
