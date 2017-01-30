package com.munch.utils.random;

/**
 * Created by Fuxing
 * Date: 27/6/2015
 * Time: 12:47 PM
 * Project: PuffinCore
 */
public class FunctionPair {

    public final Integer left;
    public final Function right;

    public static FunctionPair of(int left, Function right) {
        return new FunctionPair(left, right);
    }

    public static <T> ReduceFunctionPair<T> reduce(int left, ReduceFunctionPair.ReduceFunction<T> right) {
        return new ReduceFunctionPair<>(left, right);
    }

    public FunctionPair(int left, Function right) {
        this.left = left;
        this.right = right;
    }

    public Integer getLeft() {
        return left;
    }

    public Function getRight() {
        return right;
    }

    @FunctionalInterface
    public interface Function {
        void run(int chance);
    }
}
