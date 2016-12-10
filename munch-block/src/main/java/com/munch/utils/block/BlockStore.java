package com.munch.utils.block;

import java.util.Iterator;

/**
 * Created by: Fuxing
 * Date: 15/9/2016
 * Time: 1:11 AM
 * Project: essential
 */
public interface BlockStore {

    /**
     * Save to store with unique key and content
     *
     * @param key     unique key
     * @param content content in string
     */
    void save(String key, String content);

    /**
     * Load a block with a key
     *
     * @param key unique key of block
     * @return block content in string
     */
    String load(String key);

    /**
     * Remove a block with a key
     *
     * @param key unique key of block
     */
    void remove(String key);

    /**
     * Optional iterator method, block store must implement it
     *
     * @return Iterator
     */
    default Iterator<String> iterator() {
        throw new UnsupportedOperationException("iterator");
    }
}
