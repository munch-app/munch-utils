package com.munch.hibernate.utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

/**
 * Thread-Safe Singleton Hibernate Util
 * Only use this if there is no default implementation available on your platform.
 * Created by Fuxing
 * Date: 1/1/2015
 * Time: 5:49 PM
 * Project: PuffinCore
 */
public final class HibernateUtils {
    public static final String DEFAULT_PERSISTENCE_UNIT = "defaultPersistenceUnit";

    private static Map<String, EntityManagerFactory> factories = new HashMap<>();

    private HibernateUtils() {/* NOT Suppose to init */}

    /**
     * @param properties nullable properties for overriding
     * @return created TransactionProvider
     */
    public static void setupFactory(Map<String, String> properties) {
        setupFactory(DEFAULT_PERSISTENCE_UNIT, properties);
    }

    /**
     * @param unitName   persistence unit name
     * @param properties nullable properties for overriding
     * @return created TransactionProvider
     */
    public static void setupFactory(String unitName, Map<String, String> properties) {
        if (!factories.containsKey(unitName)) {
            synchronized (HibernateUtils.class) {
                if (!factories.containsKey(unitName)) {
                    // Setup Factory & put to map
                    factories.put(unitName, Persistence.createEntityManagerFactory(unitName, properties));
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
    }

    /**
     * Shutdown the default instance
     * Thread-safe
     */
    public static void shutdown(String unitName) {
        if (factories.containsKey(unitName)) {
            synchronized (HibernateUtils.class) {
                if (factories.containsKey(unitName)) {
                    factories.remove(unitName).close();
                }
            }
        }
    }

    /**
     * Shutdown all factory
     */
    public static void shutdownAll() {
        for (String unitName : factories.keySet()) {
            shutdown(unitName);
        }
    }

    /**
     * @param unitName persistence unit name
     * @return TransactionProvider of unit, null if don't exist
     */
    public static TransactionProvider get(String unitName) {
        EntityManagerFactory factory = factories.get(unitName);
        if (factory == null) return null;
        return new TransactionProvider(unitName, factory);
    }

    /**
     * @return default TransactionProvider
     */
    public static TransactionProvider get() {
        return get(DEFAULT_PERSISTENCE_UNIT);
    }
}