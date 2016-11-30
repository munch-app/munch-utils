package com.munch.utils.block;

import com.amazonaws.services.s3.AmazonS3;

/**
 * Created by: Fuxing
 * Date: 30/11/2016
 * Time: 8:26 PM
 * Project: munch-utils
 */
public class AwsMigrateBlockMapper<T extends VersionedBlock> extends MigrateBlockMapper<T> {

    public AwsMigrateBlockMapper(Class<T> clazz, String bucket, AmazonS3 amazonS3, BlockMigration<T> blockMigrate) {
        super(clazz, new BlockStoreMapper<>(new AwsBlockStore(bucket, amazonS3), new GsonConverter()), blockMigrate);
    }

}
