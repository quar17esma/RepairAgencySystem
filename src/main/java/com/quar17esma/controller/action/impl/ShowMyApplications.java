package com.quar17esma.controller.action.impl;

import com.quar17esma.controller.action.Action;
import com.quar17esma.controller.manager.ConfigurationManager;
import com.quar17esma.entity.Application;
import com.quar17esma.entity.User;
import com.quar17esma.service.IApplicationService;
import com.quar17esma.service.impl.ApplicationService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowMyApplications implements Action {
    private static final int DEFAULT_PAGE = 1;
    private static final int APPLICATIONS_ON_PAGE = 5;
    private IApplicationService applicationService;

    public ShowMyApplications() {
        this.applicationService = ApplicationService.getInstance();
    }

    public ShowMyApplications(IApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int page = setPageOrGetDefault(request.getParameter("page"));
        User user = (User) request.getSession().getAttribute("user");

        List<Application> applications = applicationService.getByUserIdByPage(user.getId(), page, APPLICATIONS_ON_PAGE);

        long applicationQuantity = applicationService.getAllByUserIdQuantity(user.getId());
        int pagesQuantity = countPagesQuantity(applicationQuantity);

        request.setAttribute("applications", applications);
        request.setAttribute("pagesQuantity", pagesQuantity);

        return ConfigurationManager.getProperty("path.page.applications");
    }

    private int countPagesQuantity(long applicationQuantity) {
        int pagesQuantity;

        if (applicationQuantity % APPLICATIONS_ON_PAGE != 0) {
            pagesQuantity = (int) (applicationQuantity / APPLICATIONS_ON_PAGE + 1);
        } else {
            pagesQuantity = (int) (applicationQuantity / APPLICATIONS_ON_PAGE);
        }

        return pagesQuantity;
    }

    private int setPageOrGetDefault(String pageString) {
        int page;

        if (pageString != null) {
            page = Integer.parseInt(pageString);
        } else {
            page = DEFAULT_PAGE;
        }

        return page;
    }
}
