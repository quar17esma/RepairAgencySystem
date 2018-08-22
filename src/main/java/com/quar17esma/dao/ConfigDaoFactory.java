package com.quar17esma.dao;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigDaoFactory {
    private static final Logger LOGGER = Logger.getLogger(ConfigDaoFactory.class);

    private String factoryClassName;

    private ConfigDaoFactory() {
        load();
    }

    private static class Holder {
        private static ConfigDaoFactory INSTANCE = new ConfigDaoFactory();
    }

    public static ConfigDaoFactory getInstance() {
        return Holder.INSTANCE;
    }

    private void load() {
        try (InputStream in = this.getClass().getResourceAsStream("/db.properties")) {
            Properties dbProperties = new Properties();
            dbProperties.load(in);

            factoryClassName = dbProperties.getProperty("db.factory.class");
        } catch (IOException e) {
            LOGGER.error("Fail to load DB config dao factory", e);
            throw new RuntimeException(e);
        }
    }

    public String getFactoryClassName() {
        return factoryClassName;
    }
}
