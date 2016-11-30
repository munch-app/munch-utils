package com.munch.utils.file;

import com.amazonaws.regions.Regions;

/**
 * Created by: Fuxing
 * Date: 30/11/2016
 * Time: 11:26 PM
 * Project: munch-utils
 */
public class AwsFileEndpoint implements FileEndpoint {

    protected final Regions region;
    protected final String bucket;

    public AwsFileEndpoint(Regions region, String bucket) {
        this.region = region;
        this.bucket = bucket;
    }

    @Override
    public String getUrl(String key) {
        return String.format("http://s3-%s.amazonaws.com/%s/%s", region.getName(), bucket, key);
    }

}
