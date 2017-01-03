
package com.munch.hibernate.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by Fuxing
 * Date: 8/7/2015
 * Time: 4:07 PM
 * Project: PuffinCore
 */
public class TransactionProvider {

    private EntityManagerFactory factory;

    public TransactionProvider(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public EntityManagerFactory getFactory() {
        return factory;
    }

    public void with(Transaction transaction) {
        with(transaction, transaction);
    }

    /**
     * Run jpa style transaction in functional style
     *
     * @param transaction transaction to apply
     */
    public void with(Transaction transaction, TransactionError error) {
        // Create and start
        EntityManager entityManager = factory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            // Run
            transaction.accept(entityManager);
            // Close
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }

            // Transaction Error
            if (error.error(e)) {
                throw e;
            }
        } finally {
            entityManager.close();
        }
    }

    public <T> T reduce(ReduceTransaction<T> reduceTransaction) {
        return reduce(reduceTransaction, reduceTransaction);
    }

    /**
     * Run jpa style transaction in functional style with reduce
     *
     * @param reduceTransaction reduce transaction to apply
     * @param <T>               type of object
     * @return object
     */
    public <T> T reduce(ReduceTransaction<T> reduceTransaction, TransactionError error) {
        T object = null;
        // Create and start
        EntityManager entityManager = factory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            // Run
            object = reduceTransaction.apply(entityManager);
            // Close
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }

            // Transaction Error
            if (error.error(e)) {
                throw e;
            }
        } finally {
            entityManager.close();
        }
        return object;
    }


    public <T> Optional<T> optional(OptionalTransaction<T> optionalTransaction) {
        return reduce(optionalTransaction::optional, optionalTransaction);
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
    public <T> Optional<T> optional(OptionalTransaction<T> optionalTransaction, TransactionError error) {
        return reduce(optionalTransaction::optional, error);
    }

    public <T, U> Optional<U> mapper(OptionalTransaction<T> optionalTransaction, Function<? super T, ? extends U> mapper) {
        return reduce(em -> optionalTransaction.optional(em).map(mapper), optionalTransaction);
    }

    public <T, U> Optional<U> mapper(OptionalTransaction<T> optionalTransaction, TransactionError error, Function<? super T, ? extends U> mapper) {
        return reduce(em -> optionalTransaction.optional(em).map(mapper), error);
    }
}
