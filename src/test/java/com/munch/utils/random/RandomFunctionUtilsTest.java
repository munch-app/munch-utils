package com.munch.utils.random;

import org.junit.jupiter.api.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by Fuxing
 * Date: 27/6/2015
 * Time: 12:31 PM
 * Project: PuffinCore
 */
class RandomFunctionUtilsTest {

    @Test
    public void testRandom() throws Exception {
        final int[] ran = {0};
        RandomFunctionUtils.run(
                () -> ran[0] += 1,
                () -> ran[0] += 1
        );
        assertThat(ran[0]).isEqualTo(1);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testRandomReduce() {
        String value = RandomFunctionUtils.reduce(() -> "", () -> "");
        assertThat(value).isEqualTo("");
    }
//
//    @Test
//    public void testRandomWithProbability() throws Exception {
//        int ran = 0;
//
//        RandomFunctionUtils.random(FunctionPair.of(1, chance -> ran += 1), FunctionPair.of(5, chance -> ran += 1));
//        assertThat(ran).isEqualTo(1);
//    }
//
//    @Test
//    public void testRandomAlwaysFavor() throws Exception {
//        int ran = 0;
//
//        RandomFunctionUtils.random(FunctionPair.of(2_000_000_000, chance -> ran += 1), FunctionPair.of(1, chance -> ran += 5));
//        assertThat(ran).isEqualTo(1);
//    }
//
//    @SuppressWarnings("unchecked")
//    @Test
//    public void testRandomReduceProbability() {
//        String value = RandomFunctionUtils.randomReduce(ReduceFunctionPair.of(1, chance -> ""), ReduceFunctionPair.of(5, chance -> ""));
//        assertThat(value).isEqualTo("");
//    }
//
//    @SuppressWarnings("unchecked")
//    @Test
//    public void testRandomReduceAlwaysFavor() throws Exception {
//        int v = RandomFunctionUtils.randomReduce(ReduceFunctionPair.of(2_000_000_000, chance -> 5), ReduceFunctionPair.of(5, chance -> 6));
//        assertThat(v).isEqualTo(5);
//    }
}
