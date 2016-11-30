package com.munch.utils.block;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * Created by: Fuxing
 * Date: 17/10/2016
 * Time: 8:49 AM
 * Project: essential
 */
public class MigrateBlockMapper<T extends VersionedBlock> extends TypedBlockMapper<T> {

    protected final BlockMigration<T> blockMigrate;
    protected final JsonParser jsonParser;
    protected final GsonConverter gsonConverter;

    /**
     * Typed Block Mapper that assist in the type key mapping and incremental updates to block
     * With this mapper, key is not required for each action but key supplier is required to load and save object
     * With each load call, the block is updated
     *
     * @param clazz            class of the object
     * @param blockStoreMapper block store mapper
     * @param blockMigrate     block migrate client
     */
    public MigrateBlockMapper(Class<T> clazz, BlockStoreMapper<?, GsonConverter> blockStoreMapper, BlockMigration<T> blockMigrate) {
        super(clazz, blockStoreMapper);
        this.gsonConverter = blockStoreMapper.converter;
        this.blockMigrate = blockMigrate;
        this.jsonParser = new JsonParser();
    }

    /**
     * Load as Json Element and apply incremental updates to block
     *
     * @param key key of object
     * @return Updated Block
     */
    @Override
    public T load(String key) {
        JsonElement element = loadAsJsonElement(key);
        blockMigrate.incrementalUpdate(element.getAsJsonObject());
        return gsonConverter.getGson().fromJson(element, clazz);
    }

    /**
     * @param key key of object
     * @return Json Element of Object
     */
    protected JsonElement loadAsJsonElement(String key) {
        String json = blockStoreMapper.store.load(key);
        return jsonParser.parse(json);
    }
}
