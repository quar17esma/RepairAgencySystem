package com.quar17esma.controller.action.impl;

import com.quar17esma.controller.action.Action;
import com.quar17esma.controller.manager.ConfigurationManager;
import com.quar17esma.entity.Application;
import com.quar17esma.service.IApplicationService;
import com.quar17esma.service.impl.ApplicationService;

import javax.servlet.http.HttpServletRequest;

public class EditApplication implements Action {
    private IApplicationService applicationService;

    public EditApplication() {
        this.applicationService = ApplicationService.getInstance();
    }

    public EditApplication(IApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String applicationIdString = request.getParameter("applicationId");

        if (applicationIdString != null) {
            int applicationId = Integer.parseInt(applicationIdString);
            Application application = applicationService.getById(applicationId);
            request.setAttribute("application", application);
        }

        return ConfigurationManager.getProperty("path.page.edit.application");
    }
}
