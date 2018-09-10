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

public class DeclineApplication implements Action {
    private static final Logger LOGGER = Logger.getLogger(DeclineApplication.class);

    private IApplicationService applicationService;
    private InputApplicationChecker checker;

    public DeclineApplication() {
        this.applicationService = ApplicationService.getInstance();
        this.checker = new InputApplicationChecker();
    }

    public DeclineApplication(IApplicationService applicationService, InputApplicationChecker checker) {
        this.applicationService = applicationService;
        this.checker = checker;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String locale = (String) request.getSession().getAttribute("locale");
        String applicationId = request.getParameter("applicationId");
        String declineReason = request.getParameter("declineReason");

        try {
            checker.checkDataDecline(applicationId, declineReason);
            boolean result = declineApplication(applicationId, declineReason);
            if (result) {
                request.setAttribute("successDeclineApplicationMessage",
                        LabelManager.getProperty("message.success.decline.application", locale));
                LOGGER.info("Executed DeclineApplication action, application ID: " + applicationId);
            } else {
                request.setAttribute("errorDeclineApplicationMessage",
                        LabelManager.getProperty("message.fail.decline.application", locale));
                LOGGER.info("Fail to execute DeclineApplication action" +
                        ", applicationId: " + applicationId);
            }
        } catch (WrongDataException e) {
            handleWrongDataException(e, request, locale);
            LOGGER.info("Fail to execute DeclineApplication action, wrong data" +
                    ", applicationId: " + applicationId +
                    ", decline Reason: " + declineReason);
        } catch (NoSuchElementException e) {
            request.setAttribute("errorDeclineApplicationMessage",
                    LabelManager.getProperty("message.fail.decline.application", locale));
            LOGGER.info("Fail to execute DeclineApplication action" +
                    ", applicationId: " + applicationId);
        }

        return ConfigurationManager.getProperty("path.page.welcome");
    }

    private void handleWrongDataException(WrongDataException e, HttpServletRequest request, String locale) {
        switch (e.getMessage()) {
            case "Wrong application id":
                request.setAttribute("errorDeclineApplicationMessage",
                        LabelManager.getProperty("message.error.decline.wrong.application.id", locale));
                break;
            case "Wrong decline reason":
                request.setAttribute("errorDeclineApplicationMessage",
                        LabelManager.getProperty("message.wrong.decline.reason", locale));
                break;
        }
    }

    private boolean declineApplication(String applicationIdString, String declineReason) throws NoSuchElementException {
        long applicationId = Long.parseLong(applicationIdString);
        Application application = applicationService.getById(applicationId);
        application.setProcessDate(LocalDate.now());
        application.setStatus(Status.DECLINED);
        application.setDeclineReason(declineReason);

        return applicationService.update(application);
    }
}