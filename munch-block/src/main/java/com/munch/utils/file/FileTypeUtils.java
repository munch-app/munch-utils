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
        if (tika == null) {
            synchronized (FileTypeUtils.class) {
                if (tika == null) {
                    tika = new Tika();
                }
            }
        }
        return tika;
    }

    /**
     * Get content type from file name alone
     *
     * @param filename file name
     * @return content type
     */
    public static String getContentType(String filename) {
        return getTika().detect(filename);
    }

    /**
     * Detects the media type of the given file. The type detection is
     * based on the document content and a potential known file extension.
     *
     * @param file file
     * @return content type
     * @throws ContentTypeError unable to read
     */
    public static String getContentType(File file) throws ContentTypeError {
        try {
            return getTika().detect(file);
        } catch (IOException e) {
            throw new ContentTypeError(e);
        }
    }

    /**
     * Detects the media type of the given document. The type detection is
     * based on the first few bytes of a document.
     *
     * @param bytes bytes of file
     * @return content type
     * @throws ContentTypeError unable to read
     */
    public static String getContentType(byte[] bytes) throws ContentTypeError {
        return getTika().detect(bytes);
    }

    /**
     * Detects the media type of the given file. The type detection is
     * based on the document content and a potential known file extension.
     *
     * @param filename name of file
     * @param file     file
     * @return content type
     * @throws ContentTypeError unable to read
     */
    public static String getContentType(String filename, File file) throws ContentTypeError {
        String type = getContentType(filename);
        if (type.equalsIgnoreCase(OCTET_STREAM)) {
            type = getContentType(file);
        }
        return type;
    }

    /**
     * Detect based on potential known file extension.
     * If unable to then:
     * Detects the media type of the given document. The type detection is
     * based on the first few bytes of a document.
     *
     * @param filename name of file
     * @param bytes    bytes of file
     * @return content type
     * @throws ContentTypeError unable to read
     */
    public static String getContentType(String filename, byte[] bytes) throws ContentTypeError {
        String type = getContentType(filename);
        if (type.equalsIgnoreCase(OCTET_STREAM)) {
            type = getContentType(bytes);
        }
        return type;
    }
}
