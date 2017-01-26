package com.munch.utils.file;

/**
 * Created By: Fuxing Loh
 * Date: 26/1/2017
 * Time: 3:07 PM
 * Project: munch-utils
 */
public class FakeS3Mapper extends AwsFileMapper {

    /**
     * @param endpoint endpoint of the FakeS3, eg("http://localhost:18001")
     * @param bucket   bucket, similar to S3
     */
    public FakeS3Mapper(String endpoint, String bucket) {
        this(new FakeS3Endpoint(FakeS3Utils.createClient(endpoint), bucket));
    }

    /**
     * @param endpoint fake s3 endpoint
     */
    public FakeS3Mapper(FakeS3Endpoint endpoint) {
        super(endpoint.amazonS3, endpoint);
    }
}
