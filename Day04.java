package xyz.qzem.aoc2020;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day04 {

    private static final String fileName = "D:\\code\\workspace\\aoc2020\\src\\main\\resources\\input\\day04.txt";

    public static void main(String[] args) throws IOException {
        List<String> xs = Files.lines(Paths.get(fileName)).collect(Collectors.toList());
        xs.add("");
        solveFirst(xs);
        solveSecond(xs);
    }

    private static void solveFirst(List<String> xs) {
        int count = 0;
        Set<String> required = new HashSet<>(Arrays.asList("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"));
        Set<String> seen = new HashSet<>();
        for (String x : xs) {
            if (x.trim().isEmpty()) {
                boolean flag = true;
                for (String r : required) {
                    if (!seen.contains(r)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    count++;
                }
                seen.clear();
            } else {
                String[] kvs = x.trim().split(" ");
                for (String kv : kvs) {
                    String k = kv.split(":")[0];
                    seen.add(k);
                }
            }
        }
        System.out.println(count);
    }

    private static void solveSecond(List<String> xs) {
        int count = 0;
        Map<String, Validator> required = new HashMap<>();
        required.put("byr", new ByrValidator());
        required.put("iyr", new IyrValidator());
        required.put("eyr", new EyrValidator());
        required.put("hgt", new HgtValidator());
        required.put("hcl", new HclValidator());
        required.put("ecl", new EclValidator());
        required.put("pid", new PidValidator());
        Set<String> seen = new HashSet<>();
        for (int i = 0; i < xs.size(); i++) {
            String x = xs.get(i);
            if (x.trim().isEmpty()) {
                boolean flag = true;
                for (String r : required.keySet()) {
                    if (!seen.contains(r)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    System.out.println(i);
                    count++;
                }
                seen.clear();
            } else {
                String[] kvs = x.trim().split(" ");
                for (String kv : kvs) {
                    String k = kv.split(":")[0];
                    String v = kv.split(":")[1];
                    if (required.get(k) != null && required.get(k).validate(v)) {
                        seen.add(k);
                    }
                }
            }
        }
        System.out.println(count);
    }

    private interface Validator {
        boolean validate(String value);
    }

    private static class ByrValidator implements Validator {

        @Override
        public boolean validate(String value) {
            int d = Integer.parseInt(value);
            return inRange(d, 1920, 2002);
        }
    }

    private static class IyrValidator implements Validator {

        @Override
        public boolean validate(String value) {
            int d = Integer.parseInt(value);
            return inRange(d, 2010, 2020);
        }
    }

    private static class EyrValidator implements Validator {

        @Override
        public boolean validate(String value) {
            int d = Integer.parseInt(value);
            return inRange(d, 2020, 2030);
        }
    }

    private static class HgtValidator implements Validator {

        @Override
        public boolean validate(String value) {
            int idx = 0, n = 0;
            while (idx < value.length() && Character.isDigit(value.charAt(idx))) {
                n = n * 10 + value.charAt(idx) - '0';
                idx++;
            }
            String unit = value.substring(idx);
            if (unit.equals("cm")) {
                return inRange(n, 150, 193);
            }
            return unit.equals("in") && inRange(n, 59, 76);
        }
    }

    private static class HclValidator implements Validator {

        @Override
        public boolean validate(String value) {
            if (value.length() != 7 || !value.startsWith("#")) {
                return false;
            }
            for (int i = 1; i < 7; i++) {
                char ch = value.charAt(i);
                if (!((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'f'))) {
                    return false;
                }
            }
            return true;
        }
    }

    private static final Set<String> eyeColors = new HashSet<>(Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth"));

    private static class EclValidator implements Validator {

        @Override
        public boolean validate(String value) {
            return eyeColors.contains(value);
        }
    }

    private static class PidValidator implements Validator {

        @Override
        public boolean validate(String value) {
            if (value.length() != 9) {
                return false;
            }
            for (int i = 0; i < 9; i++) {
                if (value.charAt(i) < '0' || value.charAt(i) > '9') {
                    return false;
                }
            }
            return true;
        }
    }
    
    private static boolean inRange(int d, int lhs, int rhs) {
        return d >= lhs && d <= rhs;
    }
}
