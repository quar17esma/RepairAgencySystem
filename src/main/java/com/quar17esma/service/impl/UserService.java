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

    private UserService(DaoFactory factory) {
        super(factory);
    }

    private static class Holder {
        private static UserService INSTANCE = new UserService(DaoFactory.getInstance());
    }

    public static UserService getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User getById(long id) {
        return null;
    }

    public User getByEmail(String email) {
        Optional<User> user;
        UserDAO userDAO = factory.createUserDAO();
        user = userDAO.findByEmail(email);

        return user.get();
    }

    @Override
    public void update(User item) {
    }

    @Override
    public void delete(long id) {
    }

    public void add(User user) throws BusyEmailException {
        try {
            UserDAO userDAO = factory.createUserDAO();
            Optional<User> userOptional = userDAO.findByEmail(user.getEmail());
            if (userOptional.isPresent()) {
                throw new BusyEmailException("Fail to register user, email is busy",
                        user.getName(), user.getEmail());
            }
            long userId = userDAO.insert(user);
            user.setId(userId);
        } catch (BusyEmailException e) {
            throw new BusyEmailException(e);
        }
    }
}
