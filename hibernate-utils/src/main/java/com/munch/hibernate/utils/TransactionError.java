package com.munch.hibernate.utils;

/**
 * Created By: Fuxing Loh
 * Date: 3/1/2017
 * Time: 4:25 PM
 * Project: munch-utils
 */
@FunctionalInterface
public interface TransactionError {

    /**
     * @param exception exception to handle
     * @return if should continue throw exception
     */
    boolean error(Exception exception);

}
