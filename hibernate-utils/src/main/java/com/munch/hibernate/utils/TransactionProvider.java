
package com.munch.hibernate.utils;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

/**
 * Created by Fuxing
 * Date: 8/7/2015
 * Time: 4:07 PM
 * Project: PuffinCore
 */
public class TransactionProvider {

    private static TransactionProvider provider = new TransactionProvider();

    /**
     * @return get the current provider
     */
    public static TransactionProvider getProvider() {
        if (provider == null) {
            synchronized (TransactionProvider.class) {
                if (provider == null) {
                    provider = new TransactionProvider();
                }
            }
        }
        return provider;
    }

    /**
     * @param provider provider to override with
     */
    public static void setProvider(TransactionProvider provider) {
        synchronized (TransactionProvider.class) {
            TransactionProvider.provider = provider;
        }
    }

    /**
     * Run jpa style transaction in functional style
     *
     * @param transaction transaction to run
     */
    public void with(Transaction transaction) {
        // Create and start
        EntityManager entityManager = HibernateUtil.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            // Run
            transaction.run(entityManager);
            // Close
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    /**
     * Run jpa style transaction in functional style with reduce
     *
     * @param reduceTransaction reduce transaction to run
     * @param <T>               type of object
     * @return object
     */
    public <T> T reduce(ReduceTransaction<T> reduceTransaction) {
        T object;
        // Create and start
        EntityManager entityManager = HibernateUtil.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            // Run
            object = reduceTransaction.run(entityManager);
            // Close
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
        return object;
    }

    /**
     * Run jpa style transaction in functional style with optional transaction
     * Optional Transaction are basically reduce transaction that will
     * catch NoResultException and convert it to Optional.empty()
     * Using the default transaction provider
     *
     * @param optionalTransaction reduce transaction to run that with convert to optional
     * @param <T>                 type of object
     * @return object
     */
    public <T> Optional<T> optional(OptionalTransaction<T> optionalTransaction) {
        return reduce(em -> {
            try {
                return Optional.of(optionalTransaction.run(em));
            } catch (NoResultException e) {
                return Optional.empty();
            }
        });
    }
}
