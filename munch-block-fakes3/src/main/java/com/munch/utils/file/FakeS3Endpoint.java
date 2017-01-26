package com.munch.utils.file;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;

/**
 * Created By: Fuxing Loh
 * Date: 26/1/2017
 * Time: 3:11 PM
 * Project: munch-utils
 */
public class FakeS3Endpoint extends AwsFileEndpoint {

    protected final AmazonS3 amazonS3;

    /**
     * @param amazonS3 client instance to construct public url with
     * @param bucket   bucket in fake s3
     */
    public FakeS3Endpoint(AmazonS3 amazonS3, String bucket) {
        super(Regions.AP_SOUTHEAST_1, bucket);
        this.amazonS3 = amazonS3;
    }

    /**
     * @param key file key
     * @return dynamically generated public url of the file with client instance
     */
    @Override
    public String getUrl(String key) {
        return amazonS3.getUrl(bucket, key).toExternalForm();
    }

}
