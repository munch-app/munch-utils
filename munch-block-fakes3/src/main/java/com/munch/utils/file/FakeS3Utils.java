package com.munch.utils.file;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

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
        return AmazonS3Client.builder()
                .withCredentials(
                        new AWSStaticCredentialsProvider(new BasicAWSCredentials("foo", "bar"))
                ).withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(endpoint, "us-west-2")
                ).withPathStyleAccessEnabled(true)
                .disableChunkedEncoding()
                .build();
    }

}
