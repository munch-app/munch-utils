package com.munch.utils.file;

/**
 * Created by: Fuxing
 * Date: 30/11/2016
 * Time: 11:24 PM
 * Project: munch-utils
 */
public interface FileEndpoint {

    /**
     * @param key file key
     * @return public url of the file
     */
    String getUrl(String key);

}
