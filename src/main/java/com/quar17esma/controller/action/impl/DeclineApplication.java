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

public class DeclineApplication implements Action {
    private static final Logger LOGGER = Logger.getLogger(DeclineApplication.class);
    private IApplicationService applicationService;

    public DeclineApplication() {
        this.applicationService = ApplicationService.getInstance();
    }

    public DeclineApplication(IApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        String locale = (String) request.getSession().getAttribute("locale");
        String applicationIdString = request.getParameter("applicationId");
        String declineReason = request.getParameter("declineReason");

        if (applicationIdString != null) {
            int applicationId = Integer.parseInt(applicationIdString);
            Application application = applicationService.getById(applicationId);
            application.setProcessDate(LocalDate.now());
            application.setStatus(Status.DECLINED);
            application.setDeclineReason(declineReason);
            applicationService.update(application);

            request.setAttribute("successDeclineApplicationMessage",
                    LabelManager.getProperty("message.success.decline.application", locale));
            page = ConfigurationManager.getProperty("path.page.welcome");
            LOGGER.info("Executed DeclineApplication action, application: " + application);
        } else {
            request.setAttribute("errorCompleteApplicationMessage",
                    LabelManager.getProperty("message.error.wrong.data", locale));
            page = ConfigurationManager.getProperty("path.page.applications");
            LOGGER.info("Fail to execute DeclineApplication action, wrong data");
        }

        return page;
    }
}