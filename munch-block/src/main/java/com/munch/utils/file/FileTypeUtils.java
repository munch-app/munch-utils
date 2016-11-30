package com.munch.utils.file;

import org.apache.tika.Tika;

import java.io.File;
import java.io.IOException;

import static org.apache.tika.mime.MimeTypes.OCTET_STREAM;

/**
 * Created By: Fuxing Loh
 * Date: 3/11/2016
 * Time: 6:40 PM
 * Project: essential
 */
public class FileTypeUtils {

    private static Tika tika;

    private static Tika getTika() {
        if (tika == null){
            synchronized (FileTypeUtils.class){
                if (tika == null){
                    tika = new Tika();
                }
            }
        }
        return tika;
    }

    /**
     * Get content type from file name
     *
     * @param filename file name
     * @return content type
     */
    public static String getContentType(String filename) {
        return getTika().detect(filename);
    }

    public static String getContentType(File file) throws ContentTypeError {
        try {
            return getTika().detect(file);
        } catch (IOException e) {
            throw new ContentTypeError(e);
        }
    }

    public static String getContentType(byte[] bytes) throws ContentTypeError {
        return getTika().detect(bytes);
    }

    public static String getContentType(String filename, File file) throws ContentTypeError {
        String type = getContentType(filename);
        if (type.equalsIgnoreCase(OCTET_STREAM)) {
            type = getContentType(file);
        }
        return type;
    }

    public static String getContentType(String filename, byte[] bytes) throws ContentTypeError {
        String type = getContentType(filename);
        if (type.equalsIgnoreCase(OCTET_STREAM)) {
            type = getContentType(bytes);
        }
        return type;
    }

}
