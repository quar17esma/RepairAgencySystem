package com.quar17esma.controller.action.impl;

import com.quar17esma.controller.action.Action;
import com.quar17esma.controller.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class Logout implements Action {

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty("path.page.login");

        request.getSession().invalidate();

        return page;
    }
}
