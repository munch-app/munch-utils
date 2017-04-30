package com.munch.hibernate.utils;

import javax.persistence.EntityManagerFactory;

/**
 * Created by: Fuxing
 * Date: 1/5/2017
 * Time: 3:37 AM
 * Project: munch-utils
 */
abstract class Provider {

    protected final String unitName;
    protected final EntityManagerFactory factory;

    /**
     * @param unitName unit name of provider
     * @param factory  for provider to create entity manager
     */
    protected Provider(String unitName, EntityManagerFactory factory) {
        this.unitName = unitName;
        this.factory = factory;
    }

    /**
     * @return provided EntityFactoryFactory
     */
    public EntityManagerFactory getFactory() {
        return factory;
    }

    /**
     * @return unit name of current provider
     */
    public String getUnitName() {
        return unitName;
    }

    /**
     * @return boolean indicating whether the provider is open
     */
    public boolean isOpen() {
        return getFactory().isOpen();
    }
}
