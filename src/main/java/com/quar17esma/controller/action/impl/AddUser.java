package com.quar17esma.controller.action.impl;

import com.quar17esma.controller.action.Action;
import com.quar17esma.controller.checker.InputUserChecker;
import com.quar17esma.controller.manager.ConfigurationManager;
import com.quar17esma.controller.manager.LabelManager;
import com.quar17esma.entity.User;
import com.quar17esma.enums.Role;
import com.quar17esma.exceptions.BusyEmailException;
import com.quar17esma.service.IUserService;
import com.quar17esma.service.impl.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

public class AddUser implements Action {
    private static final Logger LOGGER = Logger.getLogger(AddUser.class);

    private IUserService userService;
    private InputUserChecker checker;

    public AddUser() {
        this.userService = UserService.getInstance();
        this.checker = new InputUserChecker();
    }

    public AddUser(IUserService userService, InputUserChecker checker) {
        this.userService = userService;
        this.checker = checker;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        String locale = (String) request.getSession().getAttribute("locale");
        String name = request.getParameter("name").trim();
        String surname = request.getParameter("surname").trim();
        String email = request.getParameter("email").trim();
        String phone = request.getParameter("phone").trim();
        String password = request.getParameter("password").trim();
        LocalDate birthDate = LocalDate.parse(request.getParameter("birthDate"));

        boolean isDataCorrect = checker.isInputDataCorrect(name, surname, email, phone, password, birthDate);

        if (isDataCorrect) {
            User user = makeUser(name, surname, email, phone, password, birthDate);
            page = registerUser(user, request, locale);
            LOGGER.info("Executed AddUser action, user: " + user);
        } else {
            setDataAttributes(request, name, surname, email, phone, birthDate);
            request.setAttribute("errorRegistrationMessage",
                    LabelManager.getProperty("message.error.wrong.data", locale));
            page = ConfigurationManager.getProperty("path.page.edit.user");
            LOGGER.info("Fail to execute AddUser action, wrong data");
        }

        return page;
    }

    private User makeUser(String name, String surname, String email, String phone, String password,
                          LocalDate birthDate) {
        return new User.Builder()
                .setName(name)
                .setSurname(surname)
                .setEmail(email)
                .setPhone(phone)
                .setPassword(password)
                .setRole(Role.USER)
                .setBirthDate(birthDate)
                .build();
    }

    private String registerUser(User user, HttpServletRequest request, String locale) {
        String page = null;

        try {
            userService.add(user);
            request.setAttribute("successRegistrationMessage",
                    LabelManager.getProperty("message.success.registration", locale));
            page = ConfigurationManager.getProperty("path.page.login");
        } catch (BusyEmailException e) {
            setDataAttributes(request, user.getName(), user.getSurname(), user.getEmail(), user.getPhone(), user.getBirthDate());
            request.setAttribute("errorBusyEmailMessage",
                    LabelManager.getProperty("message.error.busy.email", locale));
            page = ConfigurationManager.getProperty("path.page.edit.user");
            LOGGER.error("Fail to register user, user: " + user, e);
        } catch (Exception e) {
            setDataAttributes(request, user.getName(), user.getSurname(), user.getEmail(), user.getPhone(), user.getBirthDate());
            request.setAttribute("errorRegisterUserMessage",
                    LabelManager.getProperty("message.error.register.user", locale));
            page = ConfigurationManager.getProperty("path.page.edit.user");
            LOGGER.error("Fail to register user, user: " + user, e);
        }

        return page;
    }

    private void setDataAttributes(HttpServletRequest request,
                                   String name, String surname, String email, String phone, LocalDate birthDate) {
        request.setAttribute("name", name);
        request.setAttribute("surname", surname);
        request.setAttribute("email", email);
        request.setAttribute("phone", phone);
        request.setAttribute("birthDate", birthDate);
    }
}
