package com.munch.utils.block;

import com.amazonaws.services.s3.AmazonS3;

/**
 * Created by: Fuxing
 * Date: 15/9/2016
 * Time: 1:16 AM
 * Project: essential
 */
public class AwsBlockStore implements BlockStore {

    protected final String bucketName;
    protected final AmazonS3 amazonS3;

    public AwsBlockStore(String bucketName, AmazonS3 amazonS3) {
        this.bucketName = bucketName;
        this.amazonS3 = amazonS3;
    }

    @Override
    public void save(String key, String content) {
        amazonS3.putObject(bucketName, key, content);
    }

    @Override
    public String load(String key) {
        return amazonS3.getObjectAsString(bucketName, key);
    }
}
