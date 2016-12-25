package com.munch.hibernate.utils;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by: Fuxing
 * Date: 15/12/2016
 * Time: 4:10 AM
 * Project: munch-utils
 */
@FunctionalInterface
public interface OptionalTransaction<T> extends Function<EntityManager, T> {
    T apply(EntityManager em) throws NoResultException;

    /**
     * Apply with catch with no result exception
     *
     * @return Optional
     */
    default Optional<T> optional(EntityManager em) {
        try {
            return Optional.of(apply(em));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    default <U> Optional<U> mapper(EntityManager em, Function<? super T, ? extends U> mapper) {
        return optional(em).map(mapper);
    }
}
