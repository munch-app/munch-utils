package com.munch.utils.block;

/**
 * Created by: Fuxing
 * Date: 24/9/2016
 * Time: 5:45 PM
 * Project: essential
 */
public class TypedBlockMapper<B extends KeyBlock> implements BlockMapper<B> {

    protected final Class<B> clazz;
    protected final BlockStoreMapper<?, ?> blockStoreMapper;

    /**
     * Typed Block Mapper that assist in the type key mapping
     * With this mapper, key is not required for each action but key supplier is required to load and save object
     *
     * @param clazz            class of the object
     * @param blockStoreMapper block mapper to use for this typed block mapper
     */
    public TypedBlockMapper(Class<B> clazz, BlockStoreMapper blockStoreMapper) {
        this.clazz = clazz;
        this.blockStoreMapper = blockStoreMapper;
    }

    /**
     * @return key of object
     */
    private String getKey(B object) {
        if (object.getKey() != null) {
            return object.getKey();
        } else if (object.getId() != null) {
            return object.getId();
        }
        throw new RuntimeException("Key or Id not declared or override");
    }

    /**
     * @param block block object to save
     */
    @Override
    public void save(B block) {
        blockStoreMapper.save(getKey(block), block);
    }

    /**
     * @param key key of object
     * @return object with the given key
     */
    @Override
    public B load(String key) {
        return blockStoreMapper.load(clazz, key);
    }

}
