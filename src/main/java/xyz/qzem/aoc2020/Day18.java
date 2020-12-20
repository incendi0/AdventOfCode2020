package xyz.qzem.aoc2020;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day18 {

    private static final String fileName =
            Objects.requireNonNull(Day05.class.getClassLoader().getResource("input/day18.txt")).getPath();

    public static void main(String[] args) throws IOException {
        List<String> xs = Files.lines(Paths.get(fileName)).collect(Collectors.toList());
//        modify BasicCalculator
//        solveFirst(xs); // 9535936849815
//        solveSecond(xs); // 472171581333710
    }

    private static class BasicCalculator {
        private int index;
        private char token;
        private String buffer;

        private static final char EOF = (char) -1;

        public BigInteger calculate(String exp) {
            this.index = -1;
            this.buffer = exp;

            return expr();
        }

        private char peek() {
            while (index + 1 < buffer.length() && buffer.charAt(index + 1) == ' ') {
                index++;
            }
            return index + 1 == buffer.length() ? EOF : buffer.charAt(index + 1);
        }

        private void consumeOnce() {
            index++;
            token = buffer.charAt(index);
        }

        private BigInteger expr() {
            BigInteger val = term();
            while (true) {
                if (peek() == '+') {
                    consumeOnce();
                    val = val.add(term());
                } else if (peek() == '-') {
                    consumeOnce();
                    val = val.add(term().negate());
                } else {
                    break;
                }
            }
            return val;
        }

        private BigInteger term() {
            BigInteger val = factor();
            while (true) {
                if (peek() == '*') {
                    consumeOnce();
                    val = val.multiply(factor());
                } else if (peek() == '/') {
                    consumeOnce();
                    val = val.divide(factor());
                } else {
                    break;
                }
            }
            return val;
        }

        private BigInteger factor() {
            int sign = 1;
            if (peek() == '-') {
                consumeOnce();
                sign = -1;
            }
            BigInteger val;
            if (peek() == '(') {
                consumeOnce();
                val = expr();
                consumeOnce();
            } else {
                val = number();
            }
            return sign == 1 ? val : val.negate();
        }

        private BigInteger number() {
            BigInteger val = BigInteger.ZERO;
            while (Character.isDigit(peek())) {
                consumeOnce();
                val = val.multiply(BigInteger.valueOf(10)).add(new BigInteger(String.valueOf(token - '0')));
            }
            return val;
        }
    }
}
