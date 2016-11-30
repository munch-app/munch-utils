package com.munch.utils.file;

import java.io.File;
import java.io.IOException;

/**
 * Created by: Fuxing
 * Date: 30/11/2016
 * Time: 10:40 PM
 * Project: munch-utils
 */
public interface FileMapper extends FileEndpoint {

    /**
     * @param key     put file with key
     * @param file    the file
     * @param control access control type
     */
    void put(String key, File file, AccessControl control) throws ContentTypeError;

    default void put(String key, File file) throws ContentTypeError {
        put(key, file, AccessControl.Private);
    }

    /**
     * @param key remove file with given key
     */
    void remove(String key);

    void get(String key, File file) throws IOException;

    /**
     * @param key key of file to get
     * @return file with given key
     */
    default File get(String key) throws IOException {
        File tempFile = File.createTempFile(key, "");
        get(key, tempFile);
        return tempFile;
    }

}
