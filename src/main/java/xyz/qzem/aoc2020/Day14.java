package xyz.qzem.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day14 {

    private static final String fileName = "D:\\code\\workspace\\aoc2020\\src\\main\\resources\\input\\day14.txt";
    private static final Pattern pattern = Pattern.compile("mem\\[(\\d+)\\]\\s+=\\s+(\\d+)");

    public static void main(String[] args) throws IOException {
        List<String> xs = Files.lines(Paths.get(fileName)).collect(Collectors.toList());
        solveFirst(xs);
        solveSecond(xs);
    }

    private static void solveSecond(List<String> xs) {
        Map<Long, Long> map = new HashMap<>();
        String mask = null;
        for (String x : xs) {
            if (x.startsWith("mask")) {
                mask = x.split(" = ")[1];
            } else {
                Matcher m = pattern.matcher(x);
                if (m.find()) {
                    long k = Long.parseLong(m.group(1));
                    long v = Long.parseLong(m.group(2));
                    List<String> ks = doMask2(mask, k);
                    for (String s : ks) {
                        map.put(Long.parseLong(s, 2), v);
                    }
                }
            }
        }

        System.out.println(map.values().stream().reduce(0L, Long::sum));
    }

    private static List<String> doMask2(String mask, long v) {
        String vs = Long.toBinaryString(v);
        StringBuilder leftPad = new StringBuilder();
        for (int i = 0; i < 36 - vs.length(); i++) {
            leftPad.append('0');
        }
        leftPad.append(vs);
        vs = leftPad.toString();
        char[] xs = new char[mask.length()];
        int count = 0;
        List<Integer> idx = new ArrayList<>();
        for (int i = 0; i < vs.length(); i++) {
            if (mask.charAt(i) == '0') {
                xs[i] = vs.charAt(i);
            } else if (mask.charAt(i) == '1') {
                xs[i] = '1';
            } else {
                xs[i] = 'U';
                count++;
                idx.add(i);
            }
        }
        List<String> ret = new ArrayList<>();
        for (int i = 0; i < (2 << count); i++) {
            for (int j = 0; j < count; j++) {
                if (((i >> (count - j - 1)) & 1) == 1) {
                    xs[idx.get(j)] = '1';
                } else {
                    xs[idx.get(j)] = '0';
                }
            }
            ret.add(new String(xs));
        }
        return ret;
    }

    private static void solveFirst(List<String> xs) {
        Map<Integer, Long> map = new HashMap<>();
        String mask = null;
        for (String x : xs) {
            if (x.startsWith("mask")) {
                mask = x.split(" = ")[1];
            } else {
                Matcher m = pattern.matcher(x);
                if (m.find()) {
                    int k =Integer.parseInt(m.group(1));
                    long v = Long.parseLong(m.group(2));
                    map.put(k, doMask(mask, v));
                }
            }
        }

        System.out.println(map.values().stream().reduce(0L, Long::sum));
    }

    private static long doMask(String mask, long v) {
        if (mask == null) {
            return 0;
        }
        String vs = Long.toBinaryString(v);
        StringBuilder leftPad = new StringBuilder();
        for (int i = 0; i < 36 - vs.length(); i++) {
            leftPad.append('0');
        }
        leftPad.append(vs);
        vs = leftPad.toString();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vs.length(); i++) {
            if (mask.charAt(i) == 'X') {
                sb.append(vs.charAt(i));
            } else {
                sb.append(mask.charAt(i));
            }
        }
        return Long.parseLong(sb.toString(), 2);
    }
}
