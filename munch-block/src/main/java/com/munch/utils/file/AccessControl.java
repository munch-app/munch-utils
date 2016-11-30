package com.munch.utils.file;

/**
 * Created by: Fuxing
 * Date: 30/11/2016
 * Time: 10:48 PM
 * Project: munch-utils
 */
public enum AccessControl {

    /**
     * read: private
     * write: private
     */
    Private("private"),

    /**
     * read: public
     * write: private
     */
    PublicRead("public-read");

    /**
     * The Amazon S3 x-amz-acl header value representing the canned acl
     */
    private final String name;

    private AccessControl(String name) {
        this.name = name;
    }

    /**
     * Returns the Amazon S3 x-amz-acl header value for this canned acl.
     */
    public String toString() {
        return name;
    }
}
