package com.quar17esma.controller.action.impl;

import com.quar17esma.controller.action.Action;
import com.quar17esma.controller.manager.ConfigurationManager;
import com.quar17esma.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class Logout implements Action {
    private static final Logger LOGGER = Logger.getLogger(Logout.class);

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty("path.page.login");

        User user = (User) request.getSession().getAttribute("user");
        request.getSession().invalidate();

        LOGGER.info("Execute Logout action, user: " + user);
        return page;
    }
}
