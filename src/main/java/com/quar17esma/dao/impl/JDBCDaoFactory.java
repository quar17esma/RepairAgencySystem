package com.quar17esma.dao.impl;

import com.quar17esma.dao.*;

import java.sql.Connection;

public class JDBCDaoFactory extends DaoFactory {

    @Override
    public UserDAO createUserDAO(Connection connection) {
        return new JDBCUserDAO(connection);
    }

    @Override
    public ApplicationDAO createApplicationDAO(Connection connection) {
        return new JDBCApplicationDAO(connection);
    }

    @Override
    public FeedbackDAO createFeedbackDAO(Connection connection) {
        return new JDBCFeedbackDAO(connection);
    }
}
