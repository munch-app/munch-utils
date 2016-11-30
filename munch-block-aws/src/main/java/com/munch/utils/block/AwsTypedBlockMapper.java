package com.munch.utils.block;

import com.amazonaws.services.s3.AmazonS3;

/**
 * Created by: Fuxing
 * Date: 30/11/2016
 * Time: 10:18 PM
 * Project: munch-utils
 */
public class AwsTypedBlockMapper<B extends KeyBlock> extends TypedBlockMapper<B> {

    /**
     * Typed Block Mapper that assist in the type key mapping
     * With this mapper, key is not required for each action but key supplier is required to load and save object
     *
     * @param clazz    class of the object
     * @param bucket   bucket name
     * @param amazonS3 amazon s3
     */
    public AwsTypedBlockMapper(Class<B> clazz, String bucket, AmazonS3 amazonS3) {
        super(clazz, new AwsStoreMapper(bucket, amazonS3, new GsonConverter()));
    }

    public AwsTypedBlockMapper(Class<B> clazz, BlockStoreMapper blockStoreMapper) {
        super(clazz, blockStoreMapper);
    }
}
