package com.munch.hibernate.utils;

import javax.persistence.EntityManager;
import java.util.function.Consumer;

/**
 * Created by: Fuxing
 * Date: 15/12/2016
 * Time: 4:10 AM
 * Project: munch-utils
 */
@FunctionalInterface
public interface Transaction extends Consumer<EntityManager>, TransactionError {
    void accept(EntityManager em);

    default boolean error(Exception e) {
        return true;
    }

}
