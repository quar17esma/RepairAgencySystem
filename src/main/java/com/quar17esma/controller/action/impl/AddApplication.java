package com.quar17esma.controller.action.impl;

import com.quar17esma.controller.action.Action;
import com.quar17esma.controller.checker.InputApplicationChecker;
import com.quar17esma.controller.manager.ConfigurationManager;
import com.quar17esma.controller.manager.LabelManager;
import com.quar17esma.entity.Application;
import com.quar17esma.entity.User;
import com.quar17esma.enums.Status;
import com.quar17esma.exceptions.WrongDataException;
import com.quar17esma.service.IApplicationService;
import com.quar17esma.service.impl.ApplicationService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

public class AddApplication implements Action {
    private static final Logger LOGGER = Logger.getLogger(AddApplication.class);

    private IApplicationService applicationService;
    private InputApplicationChecker checker;

    public AddApplication() {
        this.applicationService = ApplicationService.getInstance();
        this.checker = new InputApplicationChecker();
    }

    public AddApplication(IApplicationService applicationService, InputApplicationChecker checker) {
        this.applicationService = applicationService;
        this.checker = checker;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        String locale = (String) request.getSession().getAttribute("locale");
        String applicationIdString = request.getParameter("applicationId");
        String product = request.getParameter("product").trim();
        String repairType = request.getParameter("repairType").trim();
        User user = (User) request.getSession().getAttribute("user");

        try {
            checker.checkData(product, repairType);
        } catch (WrongDataException e) {
            page = handleWrongDataException(e, request, locale, product, repairType);
            LOGGER.info("Fail to execute AddApplication action, wrong data" +
                    ", product: " + product +
                    ", repairType: " + repairType);
            return page;
        }

        Application application = makeNewApplication(product, repairType, user);
        addOrUpdateApplication(applicationIdString, application);

        request.setAttribute("successAddApplicationMessage",
                LabelManager.getProperty("message.success.add.application", locale));
        page = ConfigurationManager.getProperty("path.page.welcome");
        LOGGER.info("Executed AddApplication action, application: " + application);
        return page;
    }

    private String handleWrongDataException(WrongDataException e, HttpServletRequest request, String locale, String product, String repairType) {
        setDataAttributes(request, product, repairType);
        switch (e.getMessage()) {
            case "Wrong product":
                request.setAttribute("wrongProductMessage",
                        LabelManager.getProperty("message.wrong.data.max.100", locale));
                break;
            case "Wrong repair type":
                request.setAttribute("wrongRepairTypeMessage",
                        LabelManager.getProperty("message.wrong.data.max.100", locale));
                break;
        }
        return ConfigurationManager.getProperty("path.page.edit.application");
    }

    private void addOrUpdateApplication(String applicationIdString, Application application) {
        if (applicationIdString == null || applicationIdString.isEmpty()) {
            applicationService.add(application);
        } else {
            int applicationId = Integer.parseInt(applicationIdString);
            application.setId(applicationId);
            applicationService.update(application);
        }
    }

    private Application makeNewApplication(String product, String repairType, User user) {
        return new Application.Builder()
                .setProduct(product)
                .setRepairType(repairType)
                .setStatus(Status.NEW)
                .setCreateDate(LocalDate.now())
                .setUser(user)
                .build();
    }

    private void setDataAttributes(HttpServletRequest request, String product, String repairType) {
        request.setAttribute("product", product);
        request.setAttribute("repairType", repairType);
    }
}
