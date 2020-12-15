package xyz.qzem.aoc2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day15 {

    private static final int LIST_SIZE_LIMIT = 20;

    public static void main(String[] args) {
        int[] xs = new int[] {6,13,1,15,2,0};
        solve(xs, 2020);
        solve(xs, 30000000);
    }

    private static void solve(int[] xs, int times) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < xs.length; i++) {
            List<Integer> arr = new ArrayList<>();
            arr.add(i);
            map.put(xs[i], arr);
        }

        int idx = xs.length;
        int last = xs[xs.length - 1];
        int curr = 0;
        while (idx < times) {
            if (map.get(last).size() < 2) {
                curr = 0;
            } else {
                List<Integer> seen = map.get(last);
                curr = seen.get(seen.size() - 1) - seen.get(seen.size() - 2);
            }
            if (!map.containsKey(curr)) {
                map.put(curr, new ArrayList<>());
            }
            map.get(curr).add(idx);
            if (map.get(curr).size() > LIST_SIZE_LIMIT) {
                List<Integer> lst = map.get(curr);
                List<Integer> newLst = new ArrayList<>();
                newLst.add(lst.get(lst.size() - 2));
                newLst.add(lst.get(lst.size() - 1));
                map.put(curr, newLst);
                lst.clear();
                lst = null;
            }
            idx++;
            last = curr;
        }
        System.out.println(curr);
    }
}
