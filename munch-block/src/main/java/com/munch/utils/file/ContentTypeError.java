package com.munch.utils.file;

/**
 * Created By: Fuxing Loh
 * Date: 28/10/2016
 * Time: 8:10 PM
 * Project: essential
 */
public class ContentTypeError extends Exception {

    /**
     * Content-Type not found
     */
    public ContentTypeError(Throwable cause) {
        super(cause);
    }

}
