package com.munch.utils.block;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.StringUtils;
import com.munch.utils.file.AccessControl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Created by: Fuxing
 * Date: 15/9/2016
 * Time: 1:16 AM
 * Project: essential
 */
public class AwsBlockStore implements BlockStore {

    protected final String bucketName;
    protected final AmazonS3 amazonS3;
    protected final AccessControl accessControl;

    /**
     * Create Aws block store with AccessControl defaulted to private
     *
     * @param bucketName bucket name where the block reside
     * @param amazonS3   amazon s3 client
     */
    public AwsBlockStore(String bucketName, AmazonS3 amazonS3) {
        this(bucketName, amazonS3, AccessControl.Private);
    }

    /**
     * @param bucketName    bucket name where the block reside
     * @param amazonS3      amazon s3 client
     * @param accessControl access control of the object
     */
    public AwsBlockStore(String bucketName, AmazonS3 amazonS3, AccessControl accessControl) {
        this.bucketName = bucketName;
        this.amazonS3 = amazonS3;
        this.accessControl = accessControl;
    }

    @Override
    public void save(String key, String content) {
        byte[] contentBytes = content.getBytes(StringUtils.UTF8);

        InputStream is = new ByteArrayInputStream(contentBytes);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("text/plain");
        metadata.setContentLength(contentBytes.length);
        PutObjectRequest request = new PutObjectRequest(bucketName, key, is, metadata);
        switch (accessControl) {
            case PublicRead:
                request.setCannedAcl(CannedAccessControlList.PublicRead);
                break;
            case Private:
                request.setCannedAcl(CannedAccessControlList.Private);
                break;
        }
        amazonS3.putObject(request);
    }


    @Override
    public String load(String key) {
        return amazonS3.getObjectAsString(bucketName, key);
    }

    @Override
    public void remove(String key) {
        amazonS3.deleteObject(bucketName, key);
    }

    @Override
    public Iterator<String> iterator() {
        return new BlockIterator();
    }

    private class BlockIterator implements Iterator<String> {
        private ObjectListing listing = amazonS3.listObjects(bucketName);
        private Iterator<S3ObjectSummary> iterator = listing.getObjectSummaries().iterator();

        @Override
        public boolean hasNext() {
            if (iterator.hasNext()) {
                return true;
            } else {
                // Next next listing
                listing = amazonS3.listNextBatchOfObjects(listing);
                if (!listing.isTruncated()) return false;
                // Has next iterator
                iterator = listing.getObjectSummaries().iterator();
                return iterator.hasNext();
            }
        }

        @Override
        public String next() {
            return load(iterator.next().getKey());
        }
    }

}
