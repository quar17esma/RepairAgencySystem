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

public class AcceptApplication implements Action {
    private static final Logger LOGGER = Logger.getLogger(AcceptApplication.class);

    private IApplicationService applicationService;
    private InputApplicationChecker checker;

    public AcceptApplication() {
        this.applicationService = ApplicationService.getInstance();
        this.checker = new InputApplicationChecker();
    }

    public AcceptApplication(IApplicationService applicationService, InputApplicationChecker checker) {
        this.applicationService = applicationService;
        this.checker = checker;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        String locale = (String) request.getSession().getAttribute("locale");
        String applicationId = request.getParameter("applicationId");
        String price = request.getParameter("price");

        try {
            checker.checkDataAccept(applicationId, price);
            boolean result = acceptApplication(applicationId, price);
            if (result) {
                request.setAttribute("successAcceptApplicationMessage",
                        LabelManager.getProperty("message.success.accept.application", locale));
                LOGGER.info("Executed AcceptApplication action, id: " + applicationId);
            } else {
                request.setAttribute("errorAcceptApplicationMessage",
                        LabelManager.getProperty("message.fail.accept.application", locale));
                LOGGER.error("Fail to execute AcceptApplication action" +
                        ", applicationId: " + applicationId);
            }
        } catch (WrongDataException e) {
            handleWrongDataException(e, request, locale);
            LOGGER.error("Fail to execute AcceptApplication action, wrong data" +
                    ", applicationId: " + applicationId +
                    ", price: " + price, e);
        } catch (NoSuchElementException e) {
            request.setAttribute("errorAcceptApplicationMessage",
                    LabelManager.getProperty("message.fail.accept.application", locale));
            LOGGER.error("Fail to execute AcceptApplication action" +
                    ", applicationId: " + applicationId, e);
        }

        return ConfigurationManager.getProperty("path.page.welcome");
    }

    private void handleWrongDataException(WrongDataException e, HttpServletRequest request, String locale) {
        switch (e.getMessage()) {
            case "Wrong application id":
                request.setAttribute("errorAcceptApplicationMessage",
                        LabelManager.getProperty("message.wrong.application.id", locale));
                break;
            case "Wrong price":
                request.setAttribute("errorAcceptApplicationMessage",
                        LabelManager.getProperty("message.wrong.price", locale));
                break;
        }
    }

    private boolean acceptApplication(String applicationIdString, String price) throws NoSuchElementException {
        long applicationId = Long.parseLong(applicationIdString);
        Application application = applicationService.getById(applicationId);
        application.setProcessDate(LocalDate.now());
        application.setStatus(Status.ACCEPTED);
        application.setPrice(Integer.parseInt(price));

        return applicationService.update(application);
    }
}