package com.quar17esma.service.impl;

import com.quar17esma.dao.DaoFactory;
import com.quar17esma.dao.UserDAO;
import com.quar17esma.entity.User;
import com.quar17esma.exceptions.NoSuchUserException;
import com.quar17esma.service.ILoginService;
import org.apache.log4j.Logger;

import java.util.Optional;

public class LoginService extends Service implements ILoginService {
    private static final Logger LOGGER = Logger.getLogger(LoginService.class);

    private UserDAO userDAO;

    private LoginService(DaoFactory factory) {
        super(factory);
        this.userDAO = factory.createUserDAO();
    }

    private static class Holder {
        private static LoginService INSTANCE = new LoginService(DaoFactory.getInstance());
    }

    public static LoginService getInstance() {
        return Holder.INSTANCE;
    }

    public User login(String email, String password) throws NoSuchUserException {
        User user = null;

        if (email == null &&
                password == null &&
                email.isEmpty() &&
                password.isEmpty()) {
            throw new NoSuchUserException("Fail to login, email or password is null or empty", email);
        }

        Optional<User> userOptional = userDAO.login(email, password);
        if (userOptional.isPresent()) {
            user = userOptional.get();
        }

        return user;
    }
}
