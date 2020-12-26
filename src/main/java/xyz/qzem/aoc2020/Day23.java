package xyz.qzem.aoc2020;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day23 {

    public static void main(String[] args) {
        String input = "368195742";
        solveFirst(input);
        solveSecond(input);
    }

    private static void solveSecond(String input) {
        Map<Integer, Node> map = new HashMap<>();
        Node curr = null;
        for (int i = 0; i < input.length(); i++) {
            int v = input.charAt(i) - '0';
            Node n = new Node(v);
            map.put(v, n);
            if (curr != null) {
                curr.next = n;
            }
            curr = n;
        }
        for (int i = input.length() + 1; i <= 1000000; i++) {
            Node n = new Node(i);
            map.put(i, n);
            if (curr != null) {
                curr.next = n;
            }
            curr = n;
        }

        Node head = map.get(input.charAt(0) - '0');
        curr.next = head;
        for (int i = 0; i < 10000000; i++) {
            move2(head, map);
            head = head.next;
        }
        Node one = map.get(1);
        System.out.println(one.next.value);
        System.out.println(one.next.next.value);
        System.out.println((long)one.next.value * one.next.next.value);
    }

    private static void move2(Node curr, Map<Integer, Node> map) {
        int destination = curr.value - 1;
        Node tmp = curr;
        Node followingThreeStart = curr.next;
        Set<Integer> followingThreeValues = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            tmp = tmp.next;
            followingThreeValues.add(tmp.value);
        }
        Node followingThreeEnd = tmp;
        curr.next = tmp.next;
        if (destination == 0) {
            destination = 1000000;
        }
        while (followingThreeValues.contains(destination)) {
            if (--destination == 0) {
                destination = 1000000;
            }
        }

        Node destNode = map.get(destination);
        followingThreeEnd.next = destNode.next;
        destNode.next = followingThreeStart;
    }

    private static void solveFirst(String input) {
        List<Integer> cups = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            cups.add(input.charAt(i) - '0');
        }
        int start = 0;
        System.out.println(cups);
        for (int i = 0; i < 100; i++) {
            start = move(cups, start);
        }
        int idx = cups.indexOf(1);
        StringBuilder sb = new StringBuilder();
        for (int i = idx + 1; i < cups.size(); i++) {
            sb.append(cups.get(i));
        }
        for (int i = 0; i < idx; i++) {
            sb.append(cups.get(i));
        }
        System.out.println(sb.toString());
    }

    private static int move(List<Integer> cups, int currentIdx) {
        int currentVal = cups.get(currentIdx);
        int destination = cups.get(currentIdx) - 1;
        List<Integer> followingThree = new ArrayList<>();
        int index = currentIdx + 1;
        int count = 0;
        while (count < 3) {
            if (index == cups.size()) {
                index = 0;
            }
            followingThree.add(cups.get(index));
            cups.set(index, 0);
            index++;
            count++;
        }
        cups.removeIf(c -> c == 0);
        cups.removeIf(c -> c == 0);
        cups.removeIf(c -> c == 0);
        int minValue = cups.stream().min(Comparator.naturalOrder()).get();
        int maxValue = cups.stream().max(Comparator.naturalOrder()).get();
        int destIdx = -1;
        while (true) {
            for (int i = 0; i < cups.size(); i++) {
                if (cups.get(i) == destination) {
                    destIdx = i;
                    break;
                }
            }
            if (destIdx != -1) {
                break;
            }
            destination -= 1;
            if (destination < minValue) {
                destination = maxValue;
            }
        }
        if (destIdx == cups.size() - 1) {
            cups.addAll(followingThree);
        } else {
            cups.addAll(destIdx + 1, followingThree);
        }
        int ret = cups.indexOf(currentVal);
        return ret == cups.size() - 1 ? 0 : ret + 1;
    }

    private static class Node {
        int value;
        Node next;

        public Node(int value) {
            this.value = value;
        }

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

}
