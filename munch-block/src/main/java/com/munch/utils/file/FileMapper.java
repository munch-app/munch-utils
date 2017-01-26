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
     * @param key     file key
     * @param file    file to put
     * @param control access control type
     * @throws ContentTypeError content type parsing error
     */
    void put(String key, File file, AccessControl control) throws ContentTypeError;

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
