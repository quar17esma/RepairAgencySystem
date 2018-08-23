package com.quar17esma.service.impl;

import com.quar17esma.dao.ConnectionPool;
import com.quar17esma.dao.DaoFactory;
import com.quar17esma.dao.UserDAO;
import com.quar17esma.entity.User;
import com.quar17esma.exceptions.BusyEmailException;
import com.quar17esma.service.IUserService;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class UserService extends Service implements IUserService {
    private static final Logger LOGGER = Logger.getLogger(UserService.class);

    private UserService(DaoFactory factory, ConnectionPool connectionPool) {
        super(factory, connectionPool);
    }

    private static class Holder {
        private static UserService INSTANCE =
                new UserService(DaoFactory.getInstance(), ConnectionPool.getInstance());
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

        try (Connection connection = connectionPool.getConnection();
             UserDAO userDAO = factory.createUserDAO(connection)) {
            connection.setAutoCommit(false);

            user = userDAO.findByEmail(email);

            connection.commit();
            connection.setAutoCommit(true);
        } catch (Exception e) {
            LOGGER.error("Fail to get user with email = " + email, e);
            throw new RuntimeException(e);
        }

        return user.get();
    }

    @Override
    public void update(User item) {
    }

    @Override
    public void delete(long id) {
    }

    public void add(User user) throws BusyEmailException {
        try (Connection connection = connectionPool.getConnection();
             UserDAO userDAO = factory.createUserDAO(connection)) {
            connection.setAutoCommit(false);

            Optional<User> userOptional = userDAO.findByEmail(user.getEmail());
            if (userOptional.isPresent()) {
                throw new BusyEmailException("Fail to register user, email is busy",
                        user.getName(), user.getEmail());
            }
            long userId = userDAO.insert(user);
            user.setId(userId);

            connection.commit();
            connection.setAutoCommit(true);
        } catch (BusyEmailException e) {
            throw new BusyEmailException(e);
        } catch (Exception e) {
            LOGGER.error("Fail to register user: " + user, e);
            throw new RuntimeException(e);
        }
    }
}
