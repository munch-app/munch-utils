package com.munch.utils.block;

/**
 * Created by: Fuxing
 * Date: 15/9/2016
 * Time: 12:47 AM
 * Project: struct
 */
public class BlockStoreMapper<BS extends BlockStore, BC extends BlockConverter> {

    protected BS store;
    protected BC converter;

    /**
     * @param store persist client to use to persist the object
     * @param converter default json converter to use for the object
     */
    public BlockStoreMapper(BS store, BC converter) {
        this.store = store;
        this.converter = converter;
    }

    /**
     * Save Block with a key
     *
     * @param key    unique key of the object
     * @param object the java object
     */
    public void save(String key, Object object) {
        store.save(key, converter.toJson(object));
    }

    /**
     * Load the java object
     *
     * @param clazz class type of the objec
     * @param key   key of the object
     * @param <T>   Class of Object
     * @return Object from persist store
     */
    public <T> T load(Class<T> clazz, String key) {
        return converter.fromJson(clazz, store.load(key));
    }

}
