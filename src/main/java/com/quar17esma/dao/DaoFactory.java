package com.quar17esma.dao;

import org.apache.log4j.Logger;

import java.sql.Connection;

public abstract class DaoFactory {
    private static final Logger LOGGER = Logger.getLogger(DaoFactory.class);

    public abstract UserDAO createUserDAO();
    public abstract ApplicationDAO createApplicationDAO();
    public abstract FeedbackDAO createFeedbackDAO();

    public static DaoFactory getInstance(){
        String className = ConfigDaoFactory.getInstance().getFactoryClassName();
        DaoFactory factory = null;
        try {
            factory = (DaoFactory) Class.forName(className).newInstance();
            LOGGER.info("Got DaoFactory");
        } catch (Exception e) {
            LOGGER.error("Fail to get DaoFactory", e);
            throw new RuntimeException(e);
        }
        return factory;
    }
}
