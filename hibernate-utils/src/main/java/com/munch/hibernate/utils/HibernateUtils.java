package com.munch.hibernate.utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.*;
import java.util.function.Function;

/**
 * Thread-Safe Singleton Hibernate Util
 * Only use this if there is no default implementation available on your platform.
 * Created by Fuxing
 * Date: 1/1/2015
 * Time: 5:49 PM
 * Project: PuffinCore
 */
public final class HibernateUtils {

    private static Map<String, TransactionProvider> providers = new HashMap<>();
    private static TransactionProvider defaultTransaction = null;

    public static final String DEFAULT_PERSISTENCE_UNIT = "defaultPersistenceUnit";

    private HibernateUtils() {/* NOT Suppose to init */}

    /**
     * @param properties nullable properties for overriding
     */
    public static void setupFactory(Map<String, String> properties) {
        setupFactory(DEFAULT_PERSISTENCE_UNIT, properties);
        synchronized (HibernateUtils.class) {
            defaultTransaction = providers.get(DEFAULT_PERSISTENCE_UNIT);
        }
    }

    /**
     * @param unitName   persistence unit name
     * @param properties nullable properties for overriding
     */
    public static void setupFactory(String unitName, Map<String, String> properties) {
        if (!providers.containsKey(unitName)) {
            synchronized (HibernateUtils.class) {
                if (!providers.containsKey(unitName)) {
                    EntityManagerFactory factory = Persistence.createEntityManagerFactory(unitName, properties);
                    providers.put(unitName, new TransactionProvider(factory));
                    return;
                }
            }
        }
        throw new RuntimeException(new IllegalStateException("Factory already initialized."));
    }

    /**
     * Shutdown the default instance
     * Thread-safe
     */
    public static void shutdown() {
        shutdown(DEFAULT_PERSISTENCE_UNIT);
        synchronized (HibernateUtils.class) {
            defaultTransaction = null;
        }
    }

    /**
     * Shutdown the default instance
     * Thread-safe
     */
    public static void shutdown(String unitName) {
        if (providers.containsKey(unitName)) {
            synchronized (HibernateUtils.class) {
                if (providers.containsKey(unitName)) {
                    providers.remove(unitName).getFactory().close();
                }
            }
        }
    }

    public static void shutdownAll() {
        shutdown();
        for (String unitName : providers.keySet()) {
            shutdown(unitName);
        }
    }

    /**
     * @param unitName persistence unit name
     * @return TransactionProvider of unit
     */
    public static TransactionProvider get(String unitName) {
        return providers.get(unitName);
    }

    /**
     * @return default TransactionProvider
     */
    public static TransactionProvider get() {
        return defaultTransaction;
    }

    /**
     * Run jpa style transaction in functional style
     * Using the default transaction provider
     *
     * @param transaction transaction to apply
     */
    public static void with(Transaction transaction) {
        defaultTransaction.with(transaction);
    }

    /**
     * Run jpa style transaction in functional style with reduce
     * Using the default transaction provider
     *
     * @param reduceTransaction reduce transaction to apply
     * @param <T>               type of object
     * @return object
     */
    public static <T> T reduce(ReduceTransaction<T> reduceTransaction) {
        return defaultTransaction.reduce(reduceTransaction);
    }

    /**
     * Run jpa style transaction in functional style with optional transaction
     * Optional Transaction are basically reduce transaction that will
     * catch NoResultException and convert it to Optional.empty()
     * Using the default transaction provider
     *
     * @param optionalTransaction reduce transaction to apply that with convert to optional
     * @param <T>                 type of object
     * @return object
     */
    public static <T> Optional<T> optional(OptionalTransaction<T> optionalTransaction) {
        return defaultTransaction.optional(optionalTransaction);
    }

    public static <T, U> Optional<U> mapper(OptionalTransaction<T> optionalTransaction, Function<? super T, ? extends U> mapper) {
        return defaultTransaction.mapper(optionalTransaction, mapper);
    }
}

