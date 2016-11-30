package com.munch.utils.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;

/**
 * Created By: Fuxing Loh
 * Date: 28/9/2016
 * Time: 7:33 PM
 * Project: struct
 */
public class AwsFileMapper implements FileMapper {

    protected final AmazonS3 amazonS3;
    protected final AwsFileEndpoint endpoint;

    public AwsFileMapper(AmazonS3 amazonS3, AwsFileEndpoint endpoint) {
        this.amazonS3 = amazonS3;
        this.endpoint = endpoint;
    }

    private String getBucket() {
        return endpoint.bucket;
    }

    @Override
    public void put(String key, File file, AccessControl control) throws ContentTypeError {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(FileTypeUtils.getContentType(key, file));
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

    @Override
    public void remove(String key) {
        amazonS3.deleteObject(getBucket(), key);
    }

    @Override
    public void get(String key, File file) throws IOException {
        amazonS3.getObject(new GetObjectRequest(getBucket(), key), file);
    }

    @Override
    public String getUrl(String key) {
        return endpoint.getUrl(key);
    }

}
