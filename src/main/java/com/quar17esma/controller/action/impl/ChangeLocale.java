package com.quar17esma.controller.action.impl;

import com.quar17esma.controller.action.Action;
import com.quar17esma.controller.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class ChangeLocale implements Action {

    @Override
    public String execute(HttpServletRequest request) {
        String locale = request.getParameter("locale");

        request.getSession().setAttribute("locale", locale);

        return ConfigurationManager.getProperty("path.page.login");
    }
}
