package com.quar17esma.dao.impl;

import com.quar17esma.dao.ApplicationDAO;
import com.quar17esma.dao.DaoFactory;
import com.quar17esma.dao.FeedbackDAO;
import com.quar17esma.dao.UserDAO;

public class JDBCDaoFactory extends DaoFactory {

    @Override
    public UserDAO createUserDAO() {
        return JDBCUserDAO.getInstance();
    }

    @Override
    public ApplicationDAO createApplicationDAO() {
        return JDBCApplicationDAO.getInstance();
    }

    @Override
    public FeedbackDAO createFeedbackDAO() {
        return JDBCFeedbackDAO.getInstance();
    }
}
