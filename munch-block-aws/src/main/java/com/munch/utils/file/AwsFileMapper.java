package com.munch.utils.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Aws Implementation of FileMapper
 * <p>
 * Created By: Fuxing Loh
 * Date: 28/9/2016
 * Time: 7:33 PM
 * Project: munch-block-aws
 */
public class AwsFileMapper implements FileMapper {

    protected final AmazonS3 amazonS3;
    protected final AwsFileEndpoint endpoint;

    /**
     * @param amazonS3 AWS S3 Client
     * @param endpoint AWS File Endpoint (Helper)
     * @see AwsFileEndpoint
     */
    public AwsFileMapper(AmazonS3 amazonS3, AwsFileEndpoint endpoint) {
        this.amazonS3 = amazonS3;
        this.endpoint = endpoint;
    }

    /**
     * @return AWS Bucket Name
     * @see AwsFileEndpoint#bucket
     */
    private String getBucket() {
        return endpoint.bucket;
    }

    /**
     * @param key         file key
     * @param file        file to put
     * @param contentType content type
     * @param control     access control type
     */
    @Override
    public void put(String key, File file, String contentType, AccessControl control) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        metadata.setContentDisposition("inline"); // For Browser to display

        // Create request and put object
        PutObjectRequest request = new PutObjectRequest(getBucket(), key, file);
        request.setMetadata(metadata);
        put(request, control);
    }

    /**
     * @param key         file key
     * @param inputStream stream of data
     * @param length      length of content in bytes
     * @param contentType content type
     * @param control     access control type
     */
    @Override
    public void put(String key, InputStream inputStream, long length, String contentType, AccessControl control) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        metadata.setContentLength(length);
        metadata.setContentDisposition("inline"); // For Browser to display

        // Create request and put object
        PutObjectRequest request = new PutObjectRequest(getBucket(), key, inputStream, metadata);
        put(request, control);
    }

    /**
     * @param request object request
     * @param control access control
     */
    private void put(PutObjectRequest request, AccessControl control) {
        switch (control) {
            case Private:
                request.setCannedAcl(CannedAccessControlList.Private);
                break;
            case PublicRead:
                request.setCannedAcl(CannedAccessControlList.PublicRead);
                break;
        }

        // Put Object
        amazonS3.putObject(request);
    }

    /**
     * @param key remove file from bucket with key
     */
    @Override
    public void remove(String key) {
        amazonS3.deleteObject(getBucket(), key);
    }

    /**
     * @param key  file key
     * @param file file object to copy content to
     * @throws IOException Exception getting the file
     */
    @Override
    public void get(String key, File file) throws IOException {
        amazonS3.getObject(new GetObjectRequest(getBucket(), key), file);
    }

    /**
     * @param key file key
     * @return public url of the file
     */
    @Override
    public String getUrl(String key) {
        return endpoint.getUrl(key);
    }

}
