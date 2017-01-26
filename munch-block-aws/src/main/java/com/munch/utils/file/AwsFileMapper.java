package com.munch.utils.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;

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
     * This method will auto parse object content and put the correct metadata
     *
     * @param key     put file with key
     * @param file    the file
     * @param control access control type
     * @throws ContentTypeError failed content parsing
     * @see FileTypeUtils
     */
    @Override
    public void put(String key, File file, AccessControl control) throws ContentTypeError {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(FileMapper.getContentType(key, file));
        metadata.setContentDisposition("inline"); // For Browser to display

        PutObjectRequest request = new PutObjectRequest(getBucket(), key, file);
        request.withMetadata(metadata);

        switch (control) {
            case Private:
                request.withCannedAcl(CannedAccessControlList.Private);
                break;
            case PublicRead:
                request.withCannedAcl(CannedAccessControlList.PublicRead);
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
