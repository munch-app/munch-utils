package com.munch.utils.block;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

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
