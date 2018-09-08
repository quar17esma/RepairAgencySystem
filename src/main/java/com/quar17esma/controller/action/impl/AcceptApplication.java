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

public class AcceptApplication implements Action {
    private static final Logger LOGGER = Logger.getLogger(AcceptApplication.class);

    private IApplicationService applicationService;

    public AcceptApplication() {
        this.applicationService = ApplicationService.getInstance();
    }

    public AcceptApplication(IApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        String locale = (String) request.getSession().getAttribute("locale");
        String applicationIdString = request.getParameter("applicationId");
        int price = Integer.parseInt(request.getParameter("price"));

        if (applicationIdString != null) {
            Application application = acceptApplication(applicationIdString, price);

            request.setAttribute("successAcceptApplicationMessage",
                    LabelManager.getProperty("message.success.accept.application", locale));
            page = ConfigurationManager.getProperty("path.page.welcome");
            LOGGER.info("Executed AcceptApplication action, application: " + application);
        } else {
            request.setAttribute("errorCompleteApplicationMessage",
                    LabelManager.getProperty("message.error.wrong.data", locale));
            page = ConfigurationManager.getProperty("path.page.applications");
            LOGGER.info("Fail to execute AcceptApplication action");
        }

        return page;
    }

    private Application acceptApplication(String applicationIdString, int price) {
        int applicationId = Integer.parseInt(applicationIdString);
        Application application = applicationService.getById(applicationId);
        application.setProcessDate(LocalDate.now());
        application.setStatus(Status.ACCEPTED);
        application.setPrice(price);
        applicationService.update(application);
        return application;
    }
}