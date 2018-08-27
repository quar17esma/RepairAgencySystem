package com.quar17esma.controller.action.impl;

import com.quar17esma.controller.action.Action;
import com.quar17esma.controller.manager.ConfigurationManager;
import com.quar17esma.entity.Application;
import com.quar17esma.service.IApplicationService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public abstract class ShowApplications implements Action {
    protected static final int DEFAULT_PAGE = 1;
    protected static final int APPLICATIONS_ON_PAGE = 5;
    protected IApplicationService applicationService;

    @Override
    public String execute(HttpServletRequest request) {
        int page = setPageOrGetDefault(request.getParameter("page"));
        List<Application> applications = getApplications(page, request);
        long applicationQuantity = getApplicationsQuantity();
        int pagesQuantity = countPagesQuantity(applicationQuantity);
        request.setAttribute("applications", applications);
        request.setAttribute("pagesQuantity", pagesQuantity);

        return ConfigurationManager.getProperty("path.page.applications");
    }

    protected abstract List<Application> getApplications(int page, HttpServletRequest request);
    protected abstract long getApplicationsQuantity();

    protected int countPagesQuantity(long applicationQuantity) {
        int pagesQuantity;

        if (applicationQuantity % APPLICATIONS_ON_PAGE != 0) {
            pagesQuantity = (int) (applicationQuantity / APPLICATIONS_ON_PAGE + 1);
        } else {
            pagesQuantity = (int) (applicationQuantity / APPLICATIONS_ON_PAGE);
        }

        return pagesQuantity;
    }

    protected int setPageOrGetDefault(String pageString) {
        int page;

        if (pageString != null) {
            page = Integer.parseInt(pageString);
        } else {
            page = DEFAULT_PAGE;
        }

        return page;
    }
}
