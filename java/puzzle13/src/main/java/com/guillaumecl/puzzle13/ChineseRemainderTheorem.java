package com.guillaumecl.puzzle13;

import java.util.Arrays;

/**
 * This code is not mine and is taken from Rosetta Code: https://rosettacode.org/wiki/Chinese_remainder_theorem#Java
 *
 * I just couldn't be bothered to write my own implementation of this.
 */
public class ChineseRemainderTheorem {

    public static long chineseRemainder(long[] n, long[] a) {

        long prod = Arrays.stream(n).reduce(1, (i, j) -> i * j);

        long p, sm = 0;
        for (int i = 0; i < n.length; i++) {
            p = prod / n[i];
            sm += a[i] * mulInv(p, n[i]) * p;
        }
        return sm % prod;
    }

    private static long mulInv(long a, long b) {
        long b0 = b;
        long x0 = 0;
        long x1 = 1;

        if (b == 1)
            return 1;

        while (a > 1) {
            long q = a / b;
            long amb = a % b;
            a = b;
            b = amb;
            long xqx = x1 - q * x0;
            x1 = x0;
            x0 = xqx;
        }

        if (x1 < 0)
            x1 += b0;

        return x1;
    }
}
