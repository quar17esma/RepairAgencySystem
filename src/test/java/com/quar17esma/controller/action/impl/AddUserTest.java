package com.quar17esma.controller.action.impl;

import com.quar17esma.controller.checker.InputUserChecker;
import com.quar17esma.controller.manager.ConfigurationManager;
import com.quar17esma.controller.manager.LabelManager;
import com.quar17esma.entity.User;
import com.quar17esma.exceptions.BusyEmailException;
import com.quar17esma.exceptions.WrongDataException;
import com.quar17esma.service.IUserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AddUserTest {
    @Mock
    private IUserService userService;
    @Mock
    private InputUserChecker checker;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;

    @InjectMocks
    private AddUser addUser;

    private String pageExpected;
    private String locale;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String password;
    private LocalDate birthDate;
    private String successRegistrationMessage;
    private String wrongNameMessage;
    private String errorBusyEmailMessage;

    @Before
    public void setUp() throws Exception {
        locale = "en_US";
        name = "John";
        surname = "Malkovich";
        email = "malkovich@gmail.com";
        phone = "+380672393939";
        password = "123456";
        birthDate = LocalDate.now();
        successRegistrationMessage = LabelManager.getProperty("message.success.registration", locale);
        wrongNameMessage = LabelManager.getProperty("message.wrong.name", locale);
        errorBusyEmailMessage = LabelManager.getProperty("message.error.busy.email", locale);

        when(session.getAttribute("locale")).thenReturn(locale);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("name")).thenReturn(name);
        when(request.getParameter("surname")).thenReturn(surname);
        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("phone")).thenReturn(phone);
        when(request.getParameter("password")).thenReturn(password);
        when(request.getParameter("birthDate")).thenReturn(String.valueOf(birthDate));
    }

    @Test
    public void execute() throws Exception {
        pageExpected = ConfigurationManager.getProperty("path.page.login");

        String resultPage = addUser.execute(request);
        assertEquals(resultPage, pageExpected);

        verify(checker).checkData(name, surname, email, phone, password, birthDate);
        verify(userService).add(any(User.class));
        verify(request).setAttribute("successRegistrationMessage", successRegistrationMessage);
    }

    @Test()
    public void executeHandleWrongDataException() throws Exception {
        pageExpected = ConfigurationManager.getProperty("path.page.edit.user");
        name = "john";
        when(request.getParameter("name")).thenReturn(name);
        doThrow(new WrongDataException("Wrong name")).when(checker).checkData(name, surname, email, phone, password, birthDate);

        String resultPage = addUser.execute(request);
        assertEquals(resultPage, pageExpected);

        verify(checker).checkData(name, surname, email, phone, password, birthDate);
        verify(request).setAttribute("wrongNameMessage", wrongNameMessage);

        verify(userService, never()).add(any(User.class));
    }

    @Test()
    public void executeHandleBusyEmailException() throws Exception {
        pageExpected = ConfigurationManager.getProperty("path.page.edit.user");
        doThrow(new BusyEmailException()).when(userService).add(any(User.class));

        String resultPage = addUser.execute(request);
        assertEquals(resultPage, pageExpected);

        verify(checker).checkData(name, surname, email, phone, password, birthDate);
        verify(request).setAttribute("errorBusyEmailMessage", errorBusyEmailMessage);
        verify(userService).add(any(User.class));

        verify(request, never()).setAttribute("successRegistrationMessage", successRegistrationMessage);
    }
}