package com.quar17esma.controller.action.impl;

import com.quar17esma.controller.action.Action;
import com.quar17esma.controller.manager.ConfigurationManager;
import com.quar17esma.entity.Application;
import com.quar17esma.service.IApplicationService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public abstract class ShowApplications implements Action {
    protected static final int DEFAULT_PAGE = 1;
    protected static final int ITEMS_ON_PAGE = 5;
    protected IApplicationService applicationService;

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        long pageNumber = setPageNumberOrDefault(request.getParameter("page"));
        List<Application> applications = getApplications(pageNumber, request);
        long applicationQuantity = getApplicationsQuantity();
        long pagesQuantity = countPagesQuantity(applicationQuantity);
        request.setAttribute("applications", applications);
        request.setAttribute("pagesQuantity", pagesQuantity);
        page = ConfigurationManager.getProperty("path.page.applications");

        return page;
    }

    protected abstract List<Application> getApplications(long page, HttpServletRequest request);
    protected abstract long getApplicationsQuantity();

    protected long countPagesQuantity(long itemQuantity) {
        return (itemQuantity % ITEMS_ON_PAGE != 0)
                ? (itemQuantity / ITEMS_ON_PAGE + 1)
                : (itemQuantity / ITEMS_ON_PAGE);
    }

    protected long setPageNumberOrDefault(String pageString) {
        return (pageString != null) ? Integer.parseInt(pageString) : DEFAULT_PAGE;
    }
}
