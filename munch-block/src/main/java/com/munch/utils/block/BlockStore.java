package com.munch.utils.block;

/**
 * Created by: Fuxing
 * Date: 15/9/2016
 * Time: 1:11 AM
 * Project: essential
 */
public interface BlockStore {

    /**
     * Save to store with unique key and content
     * @param key unique key
     * @param content content in string
     */
    void save(String key, String content);

    String load(String key);

}
