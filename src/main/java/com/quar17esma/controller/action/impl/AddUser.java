package com.quar17esma.controller.action.impl;

import com.quar17esma.controller.action.Action;
import com.quar17esma.controller.checker.InputUserChecker;
import com.quar17esma.controller.manager.ConfigurationManager;
import com.quar17esma.controller.manager.LabelManager;
import com.quar17esma.entity.User;
import com.quar17esma.enums.Role;
import com.quar17esma.exceptions.BusyEmailException;
import com.quar17esma.exceptions.WrongDataException;
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

        try {
            checker.checkData(name, surname, email, phone, password, birthDate);
        } catch (WrongDataException e) {
            page = handleWrongDataException(e, request, locale, name, surname, email, phone, birthDate);
            LOGGER.error("Fail to execute AddUser action, wrong data", e);
            return page;
        }

        User user = makeUser(name, surname, email, phone, password, birthDate);
        page = registerUser(user, request, locale);
        LOGGER.info("Executed AddUser action, user: " + user);

        return page;
    }

    private String handleWrongDataException(WrongDataException e, HttpServletRequest request, String locale,
                                            String name, String surname, String email, String phone,
                                            LocalDate birthDate) {
        setDataAttributes(request, name, surname, email, phone, birthDate);
        switch (e.getMessage()) {
            case "Wrong name":
                request.setAttribute("wrongNameMessage",
                        LabelManager.getProperty("message.wrong.name", locale));
                break;
            case "Wrong surname":
                request.setAttribute("wrongSurnameMessage",
                        LabelManager.getProperty("message.wrong.surname", locale));
                break;
            case "Wrong email":
                request.setAttribute("wrongEmailMessage",
                        LabelManager.getProperty("message.wrong.email", locale));
                break;
            case "Wrong phone":
                request.setAttribute("wrongPhoneMessage",
                        LabelManager.getProperty("message.wrong.phone", locale));
                break;
            case "Wrong password":
                request.setAttribute("wrongPasswordMessage",
                        LabelManager.getProperty("message.wrong.password", locale));
                break;
            case "Wrong birthDate":
                request.setAttribute("wrongBirthDateMessage",
                        LabelManager.getProperty("message.wrong.birthDate", locale));
                break;
        }
        return ConfigurationManager.getProperty("path.page.edit.user");
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
