package com.quar17esma.controller.action.impl;

import com.quar17esma.entity.Application;
import com.quar17esma.service.IApplicationService;
import com.quar17esma.service.impl.ApplicationService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowAcceptedApplications extends ShowApplications {
    public ShowAcceptedApplications() {
        this.applicationService = ApplicationService.getInstance();
    }

    public ShowAcceptedApplications(IApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    protected List<Application> getApplications(long page, HttpServletRequest request) {
        return applicationService.getAcceptedByPage(page, ITEMS_ON_PAGE);
    }

    @Override
    protected long getApplicationsQuantity() {
        return applicationService.getAcceptedQuantity();
    }
}
