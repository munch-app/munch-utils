package com.munch.postgres.utils;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.munch.hibernate.utils.HibernateUtils;
import com.munch.hibernate.utils.TransactionProvider;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import munch.restful.WaitFor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.PersistenceException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Fuxing
 * Date: 7/3/2017
 * Time: 3:32 AM
 * Project: munch-core
 */
public final class PostgresModule extends AbstractModule {
    private static final Logger logger = LoggerFactory.getLogger(PostgresModule.class);

    private final String unitName;

    public PostgresModule(String unitName) {
        this.unitName = unitName;
    }

    @Override
    protected void configure() {
        requestInjection(this);
    }

    /**
     * Wait for database to be read before starting postgres module
     * Setup postgres database module
     */
    @Inject
    void setupDatabase() throws InterruptedException {
        Config postgres = ConfigFactory.load().getConfig("postgres");

        try {
            // Wait for url to be ready
            String url = postgres.getString("url");
            WaitFor.host(url.substring(5), Duration.ofSeconds(180));

            Map<String, String> properties = new HashMap<>();
            properties.put("hibernate.dialect", "com.munch.postgres.utils.JsonPostgreSQLDialect");
            properties.put("hibernate.connection.provider_class", "com.zaxxer.hikari.hibernate.HikariConnectionProvider");
            properties.put("hibernate.hikari.dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource");

            properties.put("hibernate.hikari.dataSource.url", url);
            properties.put("hibernate.hikari.dataSource.user", postgres.getString("username"));
            properties.put("hibernate.hikari.dataSource.password", postgres.getString("password"));

            // Disable by default due to this error: found [bpchar (Types#CHAR)], but expecting [char(36) (Types#VARCHAR)]
            String autoCreate = postgres.getBoolean("autoCreate") ? "update" : "none";
            properties.put("hibernate.hbm2ddl.auto", autoCreate);
            properties.put("hibernate.hikari.maximumPoolSize", postgres.getString("maxPoolSize"));

            setupFactory(properties);
        } catch (Exception e) {
            logger.error("PostgresModule fail to setup", e);
            throw e;
        }
    }

    private void setupFactory(Map<String, String> properties) throws InterruptedException {
        try {
            HibernateUtils.setupFactory(unitName, properties);
        } catch (PersistenceException pe) {
            String message = ExceptionUtils.getRootCauseMessage(pe);
            if (message.contains("FATAL: the database system is starting up")) {
                // Then sleep for 6 seconds then try again
                Thread.sleep(6000);
                HibernateUtils.setupFactory(unitName, properties);
            } else {
                throw pe;
            }
        }
    }

    @Provides
    @Singleton
    TransactionProvider provideTransactionProvider() {
        return HibernateUtils.get(unitName);
    }
}
