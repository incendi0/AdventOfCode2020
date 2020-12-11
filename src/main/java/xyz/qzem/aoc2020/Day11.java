package xyz.qzem.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day11 {

    private static final String fileName =
            Objects.requireNonNull(Day05.class.getClassLoader().getResource("input/day11.txt")).getPath();

    private static final int[][] delta = new int[][] {
            {0, 1}, {1, 0}, {-1, 0}, {0, -1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
    };

    private int m, n;
    private char[][] grid;

    public Day11(List<String> xs) {
        m = xs.size();
        n = xs.get(0).length();
        grid = new char[m][n];
        for (int i = 0; i < m; i++) {
            grid[i] = xs.get(i).toCharArray();
        }
    }

    public void solveFirst() {
        while (shift() > 0) {}
        int c = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '#') {
                    c++;
                }
            }
        }
        System.out.println(c);
    }


    public void solveSecond() {
        while (shift2() > 0) {}
        int c = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '#') {
                    c++;
                }
            }
        }
        System.out.println(c);
    }

    private int shift2() {
        int[][] count = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < 8; k++) {
                    boolean flag = false;
                    int x = delta[k][0] + i;
                    int y = delta[k][1] + j;
                    while (x >= 0 && x < m && y >= 0 && y < n) {
                        if (grid[x][y] == '#') {
                            flag = true;
                            break;
                        } else if (grid[x][y] == 'L') {
                            break;
                        }
                        x += delta[k][0];
                        y += delta[k][1];
                    }
                    count[i][j] += flag ? 1 : 0;
                }
            }
        }
        int c = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '#' && count[i][j] >= 5) {
                    grid[i][j] = 'L';
                    c++;
                } else if (grid[i][j] == 'L' && count[i][j] == 0) {
                    grid[i][j] = '#';
                    c++;
                }
            }
        }
        show();
        return c;
    }

    private int shift() {
        int[][] count = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < 8; k++) {
                    int x = delta[k][0] + i;
                    int y = delta[k][1] + j;
                    if (x < 0 || x >= m || y < 0 || y >= n) {
                        continue;
                    }
                    count[i][j] += grid[x][y] == '#' ? 1 : 0;
                }
            }
        }
        int c = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '#' && count[i][j] >= 4) {
                    grid[i][j] = 'L';
                    c++;
                } else if (grid[i][j] == 'L' && count[i][j] == 0) {
                    grid[i][j] = '#';
                    c++;
                }
            }
        }
        show();
        return c;
    }

    private void show() {
        for (int i = 0; i < m; i++) {
            System.out.println(new String(grid[i]));
        }
        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        List<String> xs = Files.lines(Paths.get(fileName)).collect(Collectors.toList());
//        new Day11(xs).solveFirst();
        new Day11(xs).solveSecond();
    }
}
