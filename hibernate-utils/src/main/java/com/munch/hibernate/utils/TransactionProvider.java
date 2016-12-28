
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

    /**
     * Run jpa style transaction in functional style
     *
     * @param transaction transaction to apply
     */
    public void with(Transaction transaction) {
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
            throw e;
        } finally {
            entityManager.close();
        }
    }

    /**
     * Run jpa style transaction in functional style with reduce
     *
     * @param reduceTransaction reduce transaction to apply
     * @param <T>               type of object
     * @return object
     */
    public <T> T reduce(ReduceTransaction<T> reduceTransaction) {
        T object;
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
     * @param optionalTransaction reduce transaction to apply that with convert to optional
     * @param <T>                 type of object
     * @return object
     */
    public <T> Optional<T> optional(OptionalTransaction<T> optionalTransaction) {
        return reduce(optionalTransaction::optional);
    }

    public <T, U> Optional<U> mapper(OptionalTransaction<T> optionalTransaction, Function<? super T, ? extends U> mapper) {
        return reduce(em -> optionalTransaction.optional(em).map(mapper));
    }
}
