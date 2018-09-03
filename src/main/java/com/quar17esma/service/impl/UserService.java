package com.quar17esma.service.impl;

import com.quar17esma.dao.DaoFactory;
import com.quar17esma.dao.UserDAO;
import com.quar17esma.entity.User;
import com.quar17esma.exceptions.BusyEmailException;
import com.quar17esma.service.IUserService;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

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
        return userDAO.findAll();
    }

    @Override
    public User getById(long id) {
        return userDAO.findById(id).get();
    }

    public User getByEmail(String email) {
        return userDAO.findByEmail(email).get();
    }

    @Override
    public void update(User user) {
        userDAO.update(user);
    }

    @Override
    public void delete(long id) {
        userDAO.delete(id);
    }

    public void add(User user) throws BusyEmailException {
        try {
            Optional<User> userOptional = userDAO.findByEmail(user.getEmail());
            if (userOptional.isPresent()) {
                throw new BusyEmailException("Fail to register user: " + user + ", email is busy",
                        user.getName(), user.getEmail());
            }
            long userId = userDAO.insert(user);
            user.setId(userId);
        } catch (BusyEmailException e) {
            LOGGER.error(e.getMessage(), e);
            throw new BusyEmailException(e);
        }
    }
}
