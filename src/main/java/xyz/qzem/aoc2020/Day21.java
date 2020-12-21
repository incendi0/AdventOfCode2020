package xyz.qzem.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Day21 {

    private static final String fileName =
            Objects.requireNonNull(Day05.class.getClassLoader().getResource("input/day21.txt")).getPath();

    public static void main(String[] args) throws IOException {
        List<String> xs = Files.lines(Paths.get(fileName)).collect(Collectors.toList());
        solveFirst(xs);
        solveSecond(xs);
    }

    private static void solveFirst(List<String> xs) {
        Map<String, Integer> count = new HashMap<>();
        Map<String, Set<String>> possibleIngredients = new HashMap<>();
        extractedMethod(xs, count, possibleIngredients);

        int ret = 0;
        for (Map.Entry<String, Integer> entry : count.entrySet()) {
            boolean contains = false;
            for (Set<String> ss : possibleIngredients.values()) {
                if (ss.contains(entry.getKey())) {
                    contains = true;
                    break;
                }
            }
            if (!contains) {
                ret += entry.getValue();
            }
        }
        System.out.println(ret);
    }

    private static void extractedMethod(List<String> xs, Map<String, Integer> count, Map<String, Set<String>> possibleIngredients) {
        for (String x : xs) {
            int idx = x.indexOf('(');
            String[] ingredients = x.substring(0, idx - 1).split(" ");
            String[] allergens = x.substring(idx + 10, x.length() - 1).split(", ");
            for (String allergen : allergens) {
                if (!possibleIngredients.containsKey(allergen)) {
                    Set<String> set = new HashSet<>(Arrays.asList(ingredients));
                    possibleIngredients.put(allergen, set);
                } else {
                    possibleIngredients.get(allergen).retainAll(Arrays.asList(ingredients));
                }
            }
            for (String ingredient : ingredients) {
                count.put(ingredient, count.getOrDefault(ingredient, 0) + 1);
            }
        }
    }

    private static void solveSecond(List<String> xs) {
        Map<String, Integer> count = new HashMap<>();
        TreeMap<String, Set<String>> possibleIngredients = new TreeMap<>();
        extractedMethod(xs, count, possibleIngredients);

        while (true) {
            Set<String> determined = new HashSet<>();
            possibleIngredients.forEach((k, v) -> {
                if (v.size() == 1) {
                    determined.addAll(v);
                }
            });
            possibleIngredients.forEach((k, v) -> {
                if (v.size() > 1) {
                    v.removeAll(determined);
                }
            });
            final boolean[] solved = {true};
            possibleIngredients.forEach((k, v) -> {
                if (v.size() > 1) {
                    solved[0] = false;
                }
            });
            if (solved[0]) {
                break;
            }
        }
        List<String> ret = new ArrayList<>();
        possibleIngredients.forEach((k, v) -> ret.add(v.iterator().next()));
        System.out.println(String.join(",", ret));
    }
}
