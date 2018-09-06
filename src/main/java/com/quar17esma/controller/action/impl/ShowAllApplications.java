package com.quar17esma.controller.action.impl;

import com.quar17esma.entity.Application;
import com.quar17esma.service.IApplicationService;
import com.quar17esma.service.impl.ApplicationService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowAllApplications extends ShowApplications {
    public ShowAllApplications() {
        this.applicationService = ApplicationService.getInstance();
    }

    public ShowAllApplications(IApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    protected List<Application> getApplications(long page, HttpServletRequest request) {
        return applicationService.getByPage(page, ITEMS_ON_PAGE);
    }

    @Override
    protected long getApplicationsQuantity() {
        return applicationService.getAllQuantity();
    }
}
