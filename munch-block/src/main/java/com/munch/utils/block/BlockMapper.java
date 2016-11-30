package com.munch.utils.block;

/**
 * Created by: Fuxing
 * Date: 30/11/2016
 * Time: 9:47 PM
 * Project: munch-utils
 */
public interface BlockMapper<B extends KeyBlock> {

    /**
     * @param block block object to save
     */
    void save(B block);

    /**
     * @param key key of object
     * @return block with the given key
     */
    B load(String key);

}
