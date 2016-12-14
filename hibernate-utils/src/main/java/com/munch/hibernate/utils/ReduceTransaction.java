package com.munch.hibernate.utils;

import javax.persistence.EntityManager;

/**
 * Created by: Fuxing
 * Date: 15/12/2016
 * Time: 4:10 AM
 * Project: munch-utils
 */
@FunctionalInterface
public interface ReduceTransaction<T> {
    T run(EntityManager em);
}
