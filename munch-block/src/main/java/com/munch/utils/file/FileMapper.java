package com.munch.utils.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by: Fuxing
 * Date: 30/11/2016
 * Time: 10:40 PM
 * Project: munch-utils
 */
public interface FileMapper extends FileEndpoint {

    /**
     * @param key         file key
     * @param file        file to put
     * @param contentType content type
     * @param control     access control type
     */
    void put(String key, File file, String contentType, AccessControl control);

    /**
     * @param key         file key
     * @param inputStream stream of data
     * @param length      length of content in bytes
     * @param contentType content type
     * @param control     access control type
     */
    void put(String key, InputStream inputStream, long length, String contentType, AccessControl control);

    /**
     * This method will auto parse object content and put the correct metadata
     *
     * @param key     file key
     * @param file    file to put
     * @param control access control type
     * @throws ContentTypeError content type parsing error
     * @see FileTypeUtils
     */
    default void put(String key, File file, AccessControl control) throws ContentTypeError {
        put(key, file, getContentType(key, file), control);
    }

    /**
     * @param key  file key
     * @param file file to put
     * @throws ContentTypeError content type parsing error
     */
    default void put(String key, File file) throws ContentTypeError {
        put(key, file, AccessControl.Private);
    }

    /**
     * @param key remove file with given key
     */
    void remove(String key);

    /**
     * @param key  file key
     * @param file file to copy object to
     * @throws IOException Read exception
     */
    void get(String key, File file) throws IOException;

    /**
     * This method will create a temp file and place the object content in it
     *
     * @param key file key
     * @return Temp file with object content
     * @see FileMapper#get(String, File)
     */
    default File get(String key) throws IOException {
        File tempFile = File.createTempFile(key, "");
        get(key, tempFile);
        return tempFile;
    }

    /**
     * @param key  file key to read file type from name
     * @param file the file to read binary from
     * @return Content-Type in String
     * @throws ContentTypeError content type parsing error
     */
    static String getContentType(String key, File file) throws ContentTypeError {
        return FileTypeUtils.getContentType(key, file);
    }
}
