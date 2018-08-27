package com.quar17esma.controller.action.impl;

import com.quar17esma.entity.Application;
import com.quar17esma.entity.User;
import com.quar17esma.service.IApplicationService;
import com.quar17esma.service.impl.ApplicationService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowMyApplications extends ShowApplications {
    private User user;

    public ShowMyApplications() {
        this.applicationService = ApplicationService.getInstance();
    }

    public ShowMyApplications(IApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    protected List<Application> getApplications(int page, HttpServletRequest request) {
        user = (User) request.getSession().getAttribute("user");
        return applicationService.getByUserIdByPage(user.getId(), page, APPLICATIONS_ON_PAGE);
    }

    @Override
    protected long getApplicationsQuantity() {
        return applicationService.getAllByUserIdQuantity(user.getId());
    }
}
