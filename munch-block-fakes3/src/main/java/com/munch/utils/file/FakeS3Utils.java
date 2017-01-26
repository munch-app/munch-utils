package com.munch.utils.file;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;

/**
 * Created By: Fuxing Loh
 * Date: 28/12/2016
 * Time: 4:55 PM
 * Project: corpus-catalyst
 */
public class FakeS3Utils {

    /**
     * @param endpoint endpoint string, eg("http://localhost:18001")
     * @return Amazon S3 Client
     */
    public static AmazonS3 createClient(String endpoint) {
        AmazonS3 amazonS3 = new AmazonS3Client(new BasicAWSCredentials("foo", "bar"));
        amazonS3.setEndpoint(endpoint);
        amazonS3.setS3ClientOptions(S3ClientOptions.builder()
                .setPathStyleAccess(true)
                .disableChunkedEncoding()
                .build()
        );
        return amazonS3;
    }

}
