package com.quar17esma.service.impl;

import com.quar17esma.dao.DaoFactory;
import com.quar17esma.dao.UserDAO;
import com.quar17esma.entity.User;
import com.quar17esma.exceptions.LoginException;
import com.quar17esma.service.ILoginService;
import com.quar17esma.service.IUserService;
import org.apache.log4j.Logger;

import java.util.Optional;

public class LoginService extends Service implements ILoginService {
    private static final Logger LOGGER = Logger.getLogger(LoginService.class);

    private IUserService userService;

    private LoginService(DaoFactory factory, IUserService userService) {
        super(factory);
        this.userService = userService;
    }

    private static class Holder {
        private static LoginService INSTANCE = new LoginService(DaoFactory.getInstance(), UserService.getInstance());
    }

    public static LoginService getInstance() {
        return Holder.INSTANCE;
    }

    public User login(String email, String password) throws LoginException {
        if (checkLogin(email, password)) {
            return userService.getByEmail(email);
        } else {
            throw new LoginException("Fail to login", email);
        }
    }

    private boolean checkLogin(String email, String password) {
        boolean result = false;

        if (email != null &&
                password != null &&
                !email.isEmpty() &&
                !password.isEmpty()) {

            try {
                UserDAO userDAO = factory.createUserDAO();
                Optional<User> user = userDAO.findByEmail(email);
                if (user.isPresent()) {
                    result = user.get().getPassword().equals(password);
                }
            } catch (Exception e) {
                LOGGER.error("Fail to find user with email = " + email, e);
                throw new RuntimeException(e);
            }
        }

        return result;
    }
}
