package com.quar17esma.controller.action.impl;

import com.quar17esma.controller.action.Action;
import com.quar17esma.controller.manager.ConfigurationManager;
import com.quar17esma.controller.manager.LabelManager;
import com.quar17esma.entity.User;
import com.quar17esma.exceptions.NoSuchUserException;
import com.quar17esma.exceptions.WrongPasswordException;
import com.quar17esma.service.IUserService;
import com.quar17esma.service.impl.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class Login implements Action {
    private static final Logger LOGGER = Logger.getLogger(Login.class);
    private IUserService userService;

    public Login() {
        this.userService = UserService.getInstance();
    }

    public Login(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        String locale = (String) request.getSession().getAttribute("locale");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            User user = userService.login(email, password);
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("locale", locale);
            page = ConfigurationManager.getProperty("path.page.welcome");
            LOGGER.info("Executed Login action, user: " + user);
        } catch (NoSuchUserException e) {
            request.setAttribute("errorNoSuchUser",
                    LabelManager.getProperty("message.error.no.such.user", locale));
            page = ConfigurationManager.getProperty("path.page.login");
            LOGGER.error("Fail to execute Login action, user email: " + email, e);
        }  catch (WrongPasswordException e) {
            request.setAttribute("errorWrongPassword",
                    LabelManager.getProperty("message.error.wrong.password", locale));
            page = ConfigurationManager.getProperty("path.page.login");
            LOGGER.error("Fail to execute Login action, user email: " + email, e);
        }

        return page;
    }
}