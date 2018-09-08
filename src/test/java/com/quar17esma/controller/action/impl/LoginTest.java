package com.quar17esma.controller.action.impl;

import com.quar17esma.controller.manager.ConfigurationManager;
import com.quar17esma.controller.manager.LabelManager;
import com.quar17esma.entity.User;
import com.quar17esma.exceptions.NoSuchUserException;
import com.quar17esma.exceptions.WrongPasswordException;
import com.quar17esma.service.IUserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginTest {
    @Mock
    private IUserService userService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;

    @InjectMocks
    private Login login;

    private String pageExpected;
    private String locale;
    private String email;
    private String password;
    private User user;
    private String errorNoSuchUser;
    private String errorWrongPassword;

    @Before
    public void setUp() throws Exception {
        locale = "en_US";
        email = "malkovich@gmail.com";
        password = "123456";
        user = new User.Builder()
                .setId(3)
                .build();
        errorNoSuchUser = LabelManager.getProperty("message.error.no.such.user", locale);
        errorWrongPassword = LabelManager.getProperty("message.error.wrong.password", locale);

        when(session.getAttribute("locale")).thenReturn(locale);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("password")).thenReturn(password);
    }

    @Test
    public void execute() throws Exception {
        pageExpected = ConfigurationManager.getProperty("path.page.welcome");
        when(userService.login(email, password)).thenReturn(user);

        String resultPage = login.execute(request);
        assertEquals(resultPage, pageExpected);

        verify(userService).login(email, password);
        verify(session).setAttribute("user", user);
        verify(session).setAttribute("locale", locale);

        verify(request, never()).setAttribute("errorNoSuchUser", errorNoSuchUser);
        verify(request, never()).setAttribute("errorWrongPassword", errorWrongPassword);
    }

    @Test
    public void executeHandleNoSuchUserException() throws Exception {
        pageExpected = ConfigurationManager.getProperty("path.page.login");
        doThrow(new NoSuchUserException()).when(userService).login(email, password);

        String resultPage = login.execute(request);
        assertEquals(resultPage, pageExpected);

        verify(userService).login(email, password);
        verify(request).setAttribute("errorNoSuchUser", errorNoSuchUser);

        verify(session, never()).setAttribute("user", user);
        verify(session, never()).setAttribute("locale", locale);
        verify(request, never()).setAttribute("errorWrongPassword", errorWrongPassword);
    }

    @Test
    public void executeHandleWrongPasswordException() throws Exception {
        pageExpected = ConfigurationManager.getProperty("path.page.login");
        doThrow(new WrongPasswordException()).when(userService).login(email, password);

        String resultPage = login.execute(request);
        assertEquals(resultPage, pageExpected);

        verify(userService).login(email, password);
        verify(request).setAttribute("errorWrongPassword", errorWrongPassword);

        verify(session, never()).setAttribute("user", user);
        verify(session, never()).setAttribute("locale", locale);
        verify(request, never()).setAttribute("errorNoSuchUser", errorNoSuchUser);
    }

}