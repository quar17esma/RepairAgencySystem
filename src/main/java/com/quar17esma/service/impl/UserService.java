package com.quar17esma.service.impl;

import com.quar17esma.dao.DaoFactory;
import com.quar17esma.dao.UserDAO;
import com.quar17esma.entity.User;
import com.quar17esma.exceptions.BusyEmailException;
import com.quar17esma.service.IUserService;
import org.apache.log4j.Logger;

import java.util.List;

public class UserService extends Service implements IUserService {
    private static final Logger LOGGER = Logger.getLogger(UserService.class);

    private UserDAO userDAO;

    private UserService(DaoFactory factory) {
        super(factory);
        this.userDAO = factory.createUserDAO();
    }

    private static class Holder {
        private static UserService INSTANCE = new UserService(DaoFactory.getInstance());
    }

    public static UserService getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public List<User> getAll() {
        List<User> users = userDAO.findAll();

        LOGGER.info("Got all users");
        return users;
    }

    @Override
    public User getById(long id) {
        User user = userDAO.findById(id).get();

        LOGGER.info("Got user by ID, user: " + user + " ID: " + id);
        return user;
    }

    public User getByEmail(String email) {
        User user = userDAO.findByEmail(email).get();

        LOGGER.info("Got user by email, user: " + user + " email: " + email);
        return user;
    }

    @Override
    public void update(User user) {
        userDAO.update(user);

        LOGGER.info("Updated user, user: " + user);
    }

    @Override
    public void delete(long id) {
        userDAO.delete(id);

        LOGGER.info("Deleted user by id, id: " + id);
    }

    public void add(User user) throws BusyEmailException {
        long userId = userDAO.insert(user);
        user.setId(userId);

        LOGGER.info("Added user, user: " + user);
    }
}
