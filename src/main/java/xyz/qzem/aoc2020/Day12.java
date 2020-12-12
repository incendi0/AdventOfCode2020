package xyz.qzem.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day12 {

    private static final String fileName =
            Objects.requireNonNull(Day05.class.getClassLoader().getResource("input/day12.txt")).getPath();

    private int direction;
    private int x, y;
    private int wx, wy;
    private static final int[][] delta = new int[][] {
            {1, 0}, {0, 1}, {-1, 0}, {0, -1}
    };

    public Day12() {
    }

    public Day12(int wx, int wy) {
        this.wx = wx;
        this.wy = wy;
    }

    public static void main(String[] args) throws IOException {
        List<String> xs = Files.lines(Paths.get(fileName)).collect(Collectors.toList());
        solveFirst(xs);
        solveSecond(xs);
    }

    private static void solveFirst(List<String> xs) {
        Day12 d = new Day12();
        for (String action : xs) {
            d.act(action);
        }
        System.out.println(d.distance());
    }

    private static void solveSecond(List<String> xs) {
        Day12 d = new Day12(10, 1);
        for (String action : xs) {
            d.act2(action);
        }
        System.out.println(d.distance());
    }

    public void act2(String action) {
        char a = action.charAt(0);
        int dis = Integer.parseInt(action.substring(1));
        switch (a) {
            case 'N':
                wy += dis;
                break;
            case 'S':
                wy -= dis;
                break;
            case 'E':
                wx += dis;
                break;
            case 'W':
                wx -= dis;
                break;
            case 'L':
                rotateLeft(dis / 90);
                break;
            case 'R':
                rotateLeft(4 - (dis % 360) / 90);
                break;
            case 'F':
                int dx = wx - x;
                int dy = wy - y;
                x += dx * dis;
                y += dy * dis;
                wx = x + dx;
                wy = y + dy;
                break;
            default:
                break;
        }
    }

    private void rotateLeft(int n) {
        if (n == 1) {
            rotateLeftOnce();
        } else if (n == 2) {
            rotateLeftTwice();
        } else if (n == 3) {
            rotateLeftOnce();
            rotateLeftTwice();
        }
    }

    private void rotateLeftOnce() {
        int dx = wx - x;
        int dy = wy - y;
        if (dx >= 0 && dy >= 0) {
            wx = x - dy;
            wy = y + dx;
        } else if (dx <= 0 && dy >= 0) {
            wx = x - dy;
            wy = y + dx;
        } else if (dx <= 0) {
            wx = x - dy;
            wy = y + dx;
        } else {
            wx = x - dy;
            wy = y + dx;
        }
    }

    private void rotateLeftTwice() {
        int dx = wx - x;
        int dy = wy - y;
        wx = x - dx;
        wy = y - dy;
    }

    public void act(String action) {
        char a = action.charAt(0);
        int dis = Integer.parseInt(action.substring(1));
        switch (a) {
            case 'N':
                y += dis;
                break;
            case 'S':
                y -= dis;
                break;
            case 'E':
                x += dis;
                break;
            case 'W':
                x -= dis;
                break;
            case 'L':
                direction = ((direction + dis / 90) % 4 + 4) % 4;
                break;
            case 'R':
                direction = ((direction - dis / 90) % 4 + 4) % 4;
                break;
            case 'F':
                x += dis * delta[direction][0];
                y += dis * delta[direction][1];
                break;
            default:
                break;
        }
    }

    public int distance() {
        return Math.abs(x) + Math.abs(y);
    }
}
