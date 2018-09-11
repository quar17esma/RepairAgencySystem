package com.quar17esma.service.impl;

import com.quar17esma.dao.DaoFactory;
import com.quar17esma.dao.UserDAO;
import com.quar17esma.entity.User;
import com.quar17esma.exceptions.NoSuchUserException;
import com.quar17esma.service.IUserService;
import org.apache.log4j.Logger;

public class UserService extends Service<User> implements IUserService {
    private UserDAO userDAO;

    private UserService(DaoFactory factory) {
        super(factory);
        this.userDAO = factory.createUserDAO();
        dao = this.userDAO;
        logger = Logger.getLogger(UserService.class);
    }

    private static class Holder {
        private static UserService INSTANCE = new UserService(DaoFactory.getInstance());
    }

    public static UserService getInstance() {
        return Holder.INSTANCE;
    }

    public User getByEmail(String email) {
        User user = userDAO.findByEmail(email).get();

        logger.info("Got user by email, user: " + user + " email: " + email);
        return user;
    }

    public User login(String email, String password) throws NoSuchUserException {
        if (email == null &&
                password == null &&
                email.isEmpty() &&
                password.isEmpty()) {
            throw new NoSuchUserException("Fail to login, email or password is null or empty", email);
        }

        User user = userDAO.login(email, password);

        logger.info("Logged in user by email, user: " + user + ", email: " + email);
        return user;
    }
}
