package com.quar17esma.controller.action.impl;

import com.quar17esma.controller.action.Action;
import com.quar17esma.controller.manager.ConfigurationManager;
import com.quar17esma.controller.manager.LabelManager;
import com.quar17esma.entity.Application;
import com.quar17esma.enums.Status;
import com.quar17esma.service.IApplicationService;
import com.quar17esma.service.impl.ApplicationService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

public class CompleteApplication implements Action {
    private static final Logger LOGGER = Logger.getLogger(CompleteApplication.class);

    private IApplicationService applicationService;

    public CompleteApplication() {
        this.applicationService = ApplicationService.getInstance();
    }

    public CompleteApplication(IApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        String locale = (String) request.getSession().getAttribute("locale");
        String applicationIdString = request.getParameter("applicationId");

        if (applicationIdString != null) {
            Application application = completeApplication(applicationIdString);

            request.setAttribute("successCompleteApplicationMessage",
                    LabelManager.getProperty("message.success.complete.application", locale));
            page = ConfigurationManager.getProperty("path.page.welcome");
            LOGGER.info("Executed CompleteApplication action, application: " + application);
        } else {
            request.setAttribute("errorCompleteApplicationMessage",
                    LabelManager.getProperty("message.error.wrong.data", locale));
            page = ConfigurationManager.getProperty("path.page.applications");
            LOGGER.info("Fail to execute CompleteApplication action, wrong data");
        }
        return page;
    }

    private Application completeApplication(String applicationIdString) {
        int applicationId = Integer.parseInt(applicationIdString);
        Application application = applicationService.getById(applicationId);
        application.setCompleteDate(LocalDate.now());
        application.setStatus(Status.COMPLETED);
        applicationService.update(application);
        return application;
    }
}
