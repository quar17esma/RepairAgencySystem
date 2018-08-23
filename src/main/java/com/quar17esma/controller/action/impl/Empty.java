package com.quar17esma.controller.action.impl;

import com.quar17esma.controller.action.Action;
import com.quar17esma.controller.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;


public class Empty implements Action {
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        page = ConfigurationManager.getProperty("path.page.login");

        return page;
    }
}
