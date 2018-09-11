package com.quar17esma.controller.action.impl;

import com.quar17esma.controller.action.Action;
import com.quar17esma.controller.checker.InputApplicationChecker;
import com.quar17esma.controller.manager.ConfigurationManager;
import com.quar17esma.controller.manager.LabelManager;
import com.quar17esma.entity.Application;
import com.quar17esma.enums.Status;
import com.quar17esma.exceptions.WrongDataException;
import com.quar17esma.service.IApplicationService;
import com.quar17esma.service.impl.ApplicationService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.NoSuchElementException;

public class CompleteApplication implements Action {
    private static final Logger LOGGER = Logger.getLogger(CompleteApplication.class);

    private IApplicationService applicationService;
    private InputApplicationChecker checker;

    public CompleteApplication() {
        this.applicationService = ApplicationService.getInstance();
        this.checker = new InputApplicationChecker();
    }

    public CompleteApplication(IApplicationService applicationService, InputApplicationChecker checker) {
        this.applicationService = applicationService;
        this.checker = checker;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String locale = (String) request.getSession().getAttribute("locale");
        String applicationId = request.getParameter("applicationId");

        try {
            checker.checkApplicationId(applicationId);
            boolean result = applicationService.completeApplication(Long.parseLong(applicationId));
            if (result) {
                request.setAttribute("successCompleteApplicationMessage",
                        LabelManager.getProperty("message.success.complete.application", locale));
                LOGGER.info("Executed CompleteApplication action, application id: " + applicationId);
            } else {
                request.setAttribute("errorCompleteApplicationMessage",
                        LabelManager.getProperty("message.fail.complete.application", locale));
                LOGGER.error("Fail to execute CompleteApplication action, applicationId: " + applicationId);
            }
        } catch (WrongDataException e) {
            request.setAttribute("errorCompleteApplicationMessage",
                    LabelManager.getProperty("message.error.complete.wrong.application.id", locale));
            LOGGER.error("Fail to execute CompleteApplication action, wrong data" +
                    ", applicationId: " + applicationId, e);
        } catch (NoSuchElementException e) {
            request.setAttribute("errorCompleteApplicationMessage",
                    LabelManager.getProperty("message.fail.complete.application", locale));
            LOGGER.error("Fail to execute CompleteApplication action, applicationId: " + applicationId, e);
        }

        return ConfigurationManager.getProperty("path.page.welcome");
    }
}
