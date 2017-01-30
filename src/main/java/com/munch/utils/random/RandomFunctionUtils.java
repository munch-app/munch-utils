package com.munch.utils.random;

import org.apache.commons.lang3.RandomUtils;

/**
 * Created by Fuxing
 * Date: 27/6/2015
 * Time: 11:38 AM
 * Project: PuffinCore
 */
public class RandomFunctionUtils extends RandomUtils {

    /**
     * @param functions functions array
     * @return sum of all chance
     */
    private static <F extends RandomReduce> long sumChance(F[] functions) {
        long sum = 0;
        for (F function : functions) {
            sum += function.chance();
        }
        return sum;
    }

    /**
     * Randomly random function with the most favorable chance having higher chance
     *
     * @param functions functions with chance to random
     * @param <F>       Reduce or Function Type
     * @return The function found
     */
    private static <F extends RandomReduce> F random(F[] functions) {
        long random = nextLong(0, sumChance(functions));
        long covered = 0;
        for (F func : functions) {
            if (covered <= random && random < covered + func.chance()) {
                return func;
            }
            covered += func.chance();
        }
        throw new RuntimeException("Problem with function utils. Should never happen.");
    }

    /**
     * @param functions random functions with bias
     */
    public static void run(RandomFunction... functions) {
        random(functions).run();
    }

    /**
     * @param c1 chance 1
     * @param f1 function 1
     * @param c2 chance 2
     * @param f2 function 2
     */
    public static void run(long c1, RandomFunction f1, long c2, RandomFunction f2) {
        run(RandomFunction.of(c1, f1), RandomFunction.of(c2, f2));
    }

    public static void run(long c1, RandomFunction f1, long c2, RandomFunction f2, long c3, RandomFunction f3) {
        run(RandomFunction.of(c1, f1), RandomFunction.of(c2, f2), RandomFunction.of(c3, f3));
    }

    public static void run(long c1, RandomFunction f1, long c2, RandomFunction f2, long c3, RandomFunction f3,
                           long c4, RandomFunction f4) {
        run(RandomFunction.of(c1, f1), RandomFunction.of(c2, f2), RandomFunction.of(c3, f3),
                RandomFunction.of(c4, f4));
    }

    public static void run(long c1, RandomFunction f1, long c2, RandomFunction f2, long c3, RandomFunction f3,
                           long c4, RandomFunction f4, long c5, RandomFunction f5) {
        run(RandomFunction.of(c1, f1), RandomFunction.of(c2, f2), RandomFunction.of(c3, f3),
                RandomFunction.of(c4, f4), RandomFunction.of(c5, f5));
    }

    public static void run(long c1, RandomFunction f1, long c2, RandomFunction f2, long c3, RandomFunction f3,
                           long c4, RandomFunction f4, long c5, RandomFunction f5, long c6, RandomFunction f6) {
        run(RandomFunction.of(c1, f1), RandomFunction.of(c2, f2), RandomFunction.of(c3, f3),
                RandomFunction.of(c4, f4), RandomFunction.of(c5, f5), RandomFunction.of(c6, f6));
    }

    public static void run(long c1, RandomFunction f1, long c2, RandomFunction f2, long c3, RandomFunction f3,
                           long c4, RandomFunction f4, long c5, RandomFunction f5, long c6, RandomFunction f6,
                           long c7, RandomFunction f7) {
        run(RandomFunction.of(c1, f1), RandomFunction.of(c2, f2), RandomFunction.of(c3, f3),
                RandomFunction.of(c4, f4), RandomFunction.of(c5, f5), RandomFunction.of(c6, f6),
                RandomFunction.of(c7, f7));
    }

    public static void run(long c1, RandomFunction f1, long c2, RandomFunction f2, long c3, RandomFunction f3,
                           long c4, RandomFunction f4, long c5, RandomFunction f5, long c6, RandomFunction f6,
                           long c7, RandomFunction f7, long c8, RandomFunction f8) {
        run(RandomFunction.of(c1, f1), RandomFunction.of(c2, f2), RandomFunction.of(c3, f3),
                RandomFunction.of(c4, f4), RandomFunction.of(c5, f5), RandomFunction.of(c6, f6),
                RandomFunction.of(c7, f7), RandomFunction.of(c8, f8));
    }

    public static void run(long c1, RandomFunction f1, long c2, RandomFunction f2, long c3, RandomFunction f3,
                           long c4, RandomFunction f4, long c5, RandomFunction f5, long c6, RandomFunction f6,
                           long c7, RandomFunction f7, long c8, RandomFunction f8, long c9, RandomFunction f9) {
        run(RandomFunction.of(c1, f1), RandomFunction.of(c2, f2), RandomFunction.of(c3, f3),
                RandomFunction.of(c4, f4), RandomFunction.of(c5, f5), RandomFunction.of(c6, f6),
                RandomFunction.of(c7, f7), RandomFunction.of(c8, f8), RandomFunction.of(c9, f9));
    }

    /**
     * @param functions reduce functions with bias
     */
    public static <R> R reduce(RandomReduce<R>... functions) {
        return random(functions).reduce();
    }

    @SuppressWarnings("unchecked")
    public static <R> R reduce(long c1, RandomReduce<R> f1, long c2, RandomReduce<R> f2) {
        return reduce(RandomReduce.of(c1, f1), RandomReduce.of(c2, f2));
    }

    @SuppressWarnings("unchecked")
    public static <R> R reduce(long c1, RandomReduce<R> f1, long c2, RandomReduce<R> f2, long c3, RandomReduce<R> f3) {
        return reduce(RandomReduce.of(c1, f1), RandomReduce.of(c2, f2), RandomReduce.of(c3, f3));
    }

    @SuppressWarnings("unchecked")
    public static <R> R reduce(long c1, RandomReduce<R> f1, long c2, RandomReduce<R> f2, long c3, RandomReduce<R> f3,
                               long c4, RandomReduce<R> f4) {
        return reduce(RandomReduce.of(c1, f1), RandomReduce.of(c2, f2), RandomReduce.of(c3, f3),
                RandomReduce.of(c4, f4));
    }

    @SuppressWarnings("unchecked")
    public static <R> R reduce(long c1, RandomReduce<R> f1, long c2, RandomReduce<R> f2, long c3, RandomReduce<R> f3,
                               long c4, RandomReduce<R> f4, long c5, RandomReduce<R> f5) {
        return reduce(RandomReduce.of(c1, f1), RandomReduce.of(c2, f2), RandomReduce.of(c3, f3),
                RandomReduce.of(c4, f4), RandomReduce.of(c5, f5));
    }

    @SuppressWarnings("unchecked")
    public static <R> R reduce(long c1, RandomReduce<R> f1, long c2, RandomReduce<R> f2, long c3, RandomReduce<R> f3,
                               long c4, RandomReduce<R> f4, long c5, RandomReduce<R> f5, long c6, RandomReduce<R> f6) {
        return reduce(RandomReduce.of(c1, f1), RandomReduce.of(c2, f2), RandomReduce.of(c3, f3),
                RandomReduce.of(c4, f4), RandomReduce.of(c5, f5), RandomReduce.of(c6, f6));
    }

    @SuppressWarnings("unchecked")
    public static <R> R reduce(long c1, RandomReduce<R> f1, long c2, RandomReduce<R> f2, long c3, RandomReduce<R> f3,
                               long c4, RandomReduce<R> f4, long c5, RandomReduce<R> f5, long c6, RandomReduce<R> f6,
                               long c7, RandomReduce<R> f7) {
        return reduce(RandomReduce.of(c1, f1), RandomReduce.of(c2, f2), RandomReduce.of(c3, f3),
                RandomReduce.of(c4, f4), RandomReduce.of(c5, f5), RandomReduce.of(c6, f6),
                RandomReduce.of(c7, f7));
    }

    @SuppressWarnings("unchecked")
    public static <R> R reduce(long c1, RandomReduce<R> f1, long c2, RandomReduce<R> f2, long c3, RandomReduce<R> f3,
                               long c4, RandomReduce<R> f4, long c5, RandomReduce<R> f5, long c6, RandomReduce<R> f6,
                               long c7, RandomReduce<R> f7, long c8, RandomReduce<R> f8) {
        return reduce(RandomReduce.of(c1, f1), RandomReduce.of(c2, f2), RandomReduce.of(c3, f3),
                RandomReduce.of(c4, f4), RandomReduce.of(c5, f5), RandomReduce.of(c6, f6),
                RandomReduce.of(c7, f7), RandomReduce.of(c8, f8));
    }

    @SuppressWarnings("unchecked")
    public static <R> R reduce(long c1, RandomReduce<R> f1, long c2, RandomReduce<R> f2, long c3, RandomReduce<R> f3,
                               long c4, RandomReduce<R> f4, long c5, RandomReduce<R> f5, long c6, RandomReduce<R> f6,
                               long c7, RandomReduce<R> f7, long c8, RandomReduce<R> f8, long c9, RandomReduce<R> f9) {
        return reduce(RandomReduce.of(c1, f1), RandomReduce.of(c2, f2), RandomReduce.of(c3, f3),
                RandomReduce.of(c4, f4), RandomReduce.of(c5, f5), RandomReduce.of(c6, f6),
                RandomReduce.of(c7, f7), RandomReduce.of(c8, f8), RandomReduce.of(c9, f9));
    }
}
