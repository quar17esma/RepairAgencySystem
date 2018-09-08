package com.quar17esma.controller.action.impl;

import com.quar17esma.controller.checker.InputApplicationChecker;
import com.quar17esma.controller.manager.ConfigurationManager;
import com.quar17esma.controller.manager.LabelManager;
import com.quar17esma.entity.Application;
import com.quar17esma.exceptions.WrongDataException;
import com.quar17esma.service.IApplicationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AddApplicationTest {
    @Mock
    private IApplicationService applicationService;
    @Mock
    private InputApplicationChecker checker;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;

    @InjectMocks
    private AddApplication addApplication;

    private static final String LOCALE = "en_US";
    private static final String SUCCESS_REGISTRATION_MESSAGE =
            LabelManager.getProperty("message.success.add.application", LOCALE);
    private static final String WRONG_PRODUCT_MESSAGE = LabelManager.getProperty("message.wrong.data.max.100", LOCALE);
    private static final String WRONG_REPAIR_TYPE_MESSAGE = LabelManager.getProperty("message.wrong.data.max.100", LOCALE);

    private String product;
    private String repairType;
    private String pageExpected;
    private String applicationIdString;

    @Before
    public void setUp() throws Exception {
        product = "Simple product";
        repairType = "Repair battery";
        applicationIdString = null;

        when(session.getAttribute("locale")).thenReturn(LOCALE);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("product")).thenReturn(product);
        when(request.getParameter("repairType")).thenReturn(repairType);
        when(request.getParameter("applicationId")).thenReturn(applicationIdString);
    }

    @Test
    public void executeAddApplication() throws Exception {
        pageExpected = ConfigurationManager.getProperty("path.page.welcome");

        String resultPage = addApplication.execute(request);
        assertEquals(resultPage, pageExpected);

        verify(checker).checkData(product, repairType);
        verify(applicationService).add(any(Application.class));
        verify(request).setAttribute("successAddApplicationMessage", SUCCESS_REGISTRATION_MESSAGE);

        verify(applicationService, never()).update(any(Application.class));
        verify(request, never()).setAttribute("wrongProductMessage", WRONG_PRODUCT_MESSAGE);
        verify(request, never()).setAttribute("wrongRepairTypeMessage", WRONG_REPAIR_TYPE_MESSAGE);
    }

    @Test
    public void executeUpdateApplication() throws Exception {
        pageExpected = ConfigurationManager.getProperty("path.page.welcome");
        applicationIdString = "3";
        when(request.getParameter("applicationId")).thenReturn(applicationIdString);

        String resultPage = addApplication.execute(request);
        assertEquals(resultPage, pageExpected);

        verify(checker).checkData(product, repairType);
        verify(applicationService).update(any(Application.class));
        verify(request).setAttribute("successAddApplicationMessage", SUCCESS_REGISTRATION_MESSAGE);

        verify(applicationService, never()).add(any(Application.class));
        verify(request, never()).setAttribute("wrongProductMessage", WRONG_PRODUCT_MESSAGE);
        verify(request, never()).setAttribute("wrongRepairTypeMessage", WRONG_REPAIR_TYPE_MESSAGE);
    }

    @Test
    public void executeHandleWrongDataException() throws Exception {
        pageExpected = ConfigurationManager.getProperty("path.page.edit.application");
        doThrow(new WrongDataException("Wrong product")).when(checker).checkData(product, repairType);

        String resultPage = addApplication.execute(request);
        assertEquals(resultPage, pageExpected);

        verify(checker).checkData(product, repairType);
        verify(request).setAttribute("wrongProductMessage", WRONG_PRODUCT_MESSAGE);

        verify(applicationService, never()).add(any(Application.class));
        verify(applicationService, never()).update(any(Application.class));
        verify(request, never()).setAttribute("wrongRepairTypeMessage", WRONG_REPAIR_TYPE_MESSAGE);
        verify(request, never()).setAttribute("successAddApplicationMessage", SUCCESS_REGISTRATION_MESSAGE);
    }
}