package com.munch.utils.random;

/**
 * Created by Fuxing
 * Date: 27/6/2015
 * Time: 12:54 PM
 * Project: PuffinCore
 */
public class ReduceFunctionPair<T> {
    public final Integer left;
    public final ReduceFunction<T> right;

    public static <T> ReduceFunctionPair<T> of(Integer left, ReduceFunction<T> right) {
        return new ReduceFunctionPair<>(left, right);
    }

    public ReduceFunctionPair(Integer left, ReduceFunction<T> right) {
        this.left = left;
        this.right = right;
    }

    public Integer getLeft() {
        return left;
    }

    public ReduceFunction<T> getRight() {
        return right;
    }

    @FunctionalInterface
    public interface ReduceFunction<T> {
        T run(int chance);
    }
}
