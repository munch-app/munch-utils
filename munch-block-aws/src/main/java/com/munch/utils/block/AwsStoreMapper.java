package com.munch.utils.block;

import com.amazonaws.services.s3.AmazonS3;

/**
 * Created by: Fuxing
 * Date: 15/9/2016
 * Time: 1:27 AM
 * Project: essential
 */
public class AwsStoreMapper extends BlockStoreMapper<AwsBlockStore, BlockConverter> {

    public AwsStoreMapper(String bucketName, AmazonS3 amazonS3, BlockConverter blockConverter) {
        super(new AwsBlockStore(bucketName, amazonS3), blockConverter);
    }

}
