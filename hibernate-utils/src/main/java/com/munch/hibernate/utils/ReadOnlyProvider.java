package com.munch.hibernate.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Optional;

/**
 * Created by: Fuxing
 * Date: 1/5/2017
 * Time: 3:36 AM
 * Project: munch-utils
 */
public class ReadOnlyProvider extends Provider {

    /**
     * @param unitName unit name of provider
     * @param factory  for provider to create entity manager
     */
    public ReadOnlyProvider(String unitName, EntityManagerFactory factory) {
        super(unitName, factory);
    }

    /**
     * Run JPA style transaction in lambda
     *
     * @param transaction transaction lambda
     */
    public void with(Transaction transaction) {
        with(transaction, transaction);
    }

    /**
     * Run JPA style transaction in lambda
     *
     * @param transaction transaction lambda
     * @param error       error lambda to run if error is thrown
     */
    public void with(Transaction transaction, TransactionError error) {
        // Create and start
        EntityManager entityManager = factory.createEntityManager();
        try {
            transaction.accept(entityManager);
        } catch (Exception e) {
            // Transaction Error
            if (error.error(e)) {
                throw e;
            }
        } finally {
            entityManager.close();
        }
    }

    /**
     * Run JPA style transaction in functional style with reduce
     *
     * @param reduceTransaction reduce transaction to apply
     * @param <T>               type of object
     * @return object
     */
    public <T> T reduce(ReduceTransaction<T> reduceTransaction) {
        return reduce(reduceTransaction, reduceTransaction);
    }

    /**
     * Run JPA style transaction in functional style with reduce
     *
     * @param reduceTransaction reduce transaction to apply
     * @param error             error lambda to run if error is thrown
     * @param <T>               type of object
     * @return object
     */
    public <T> T reduce(ReduceTransaction<T> reduceTransaction, TransactionError error) {
        T object = null;
        // Create and start
        EntityManager entityManager = factory.createEntityManager();
        try {
            object = reduceTransaction.apply(entityManager);
        } catch (Exception e) {
            // Transaction Error
            if (error.error(e)) {
                throw e;
            }
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
        return reduce(optionalTransaction::optional, optionalTransaction);
    }

    /**
     * Run jpa style transaction in functional style with optional transaction
     * Optional Transaction are basically reduce transaction that will
     * catch NoResultException and convert it to Optional.empty()
     * Using the default transaction provider
     *
     * @param optionalTransaction reduce transaction to apply that with convert to optional
     * @param error               lambda to run if error is thrown
     * @param <T>                 type of object
     * @return object
     */
    public <T> Optional<T> optional(OptionalTransaction<T> optionalTransaction, TransactionError error) {
        return reduce(optionalTransaction::optional, error);
    }

}
