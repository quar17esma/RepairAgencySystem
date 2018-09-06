package com.quar17esma.controller.action.impl;

import com.quar17esma.controller.action.Action;
import com.quar17esma.controller.checker.InputApplicationChecker;
import com.quar17esma.controller.manager.ConfigurationManager;
import com.quar17esma.controller.manager.LabelManager;
import com.quar17esma.entity.Application;
import com.quar17esma.entity.User;
import com.quar17esma.enums.Status;
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

        boolean isDataCorrect = checker.isInputDataCorrect(product, repairType);

        if (isDataCorrect) {
            Application application = makeNewApplication(product, repairType, user);

            if (applicationIdString != null && !applicationIdString.isEmpty()) {
                int applicationId = Integer.parseInt(applicationIdString);
                application.setId(applicationId);
                applicationService.update(application);
            } else {
                applicationService.add(application);
            }

            request.setAttribute("successAddApplicationMessage",
                    LabelManager.getProperty("message.success.add.application", locale));
            page = ConfigurationManager.getProperty("path.page.welcome");
            LOGGER.info("Executed AddApplication action, application: " + application);
        } else {
            request.setAttribute("errorAddApplicationMessage",
                    LabelManager.getProperty("message.error.wrong.data", locale));
            page = ConfigurationManager.getProperty("path.page.edit.application");
            LOGGER.info("Fail to execute AddApplication action, request: " + request);
        }

        return page;
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
}
