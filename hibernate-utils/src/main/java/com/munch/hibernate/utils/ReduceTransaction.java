package com.munch.hibernate.utils;

import javax.persistence.EntityManager;
import java.util.function.Function;

/**
 * Created by: Fuxing
 * Date: 15/12/2016
 * Time: 4:10 AM
 * Project: munch-utils
 */
@FunctionalInterface
public interface ReduceTransaction<T> extends Function<EntityManager, T> {
    T apply(EntityManager em);
}
