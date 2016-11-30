package com.munch.utils.block;

/**
 * Created by: Fuxing
 * Date: 30/11/2016
 * Time: 7:53 PM
 * Project: munch-utils
 */
public interface KeyBlock {

    /**
     * @return key of block
     */
    default String getKey() {
        return null;
    }

    /**
     * @return id of block
     */
    default String getId() {
        return null;
    }

}
