package com.quar17esma.controller.action.impl;

import com.quar17esma.controller.action.Action;
import com.quar17esma.controller.checker.InputFeedbackChecker;
import com.quar17esma.controller.manager.ConfigurationManager;
import com.quar17esma.controller.manager.LabelManager;
import com.quar17esma.entity.Application;
import com.quar17esma.entity.Feedback;
import com.quar17esma.entity.User;
import com.quar17esma.exceptions.WrongDataException;
import com.quar17esma.service.IFeedbackService;
import com.quar17esma.service.impl.FeedbackService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AddFeedback implements Action {
    private static final Logger LOGGER = Logger.getLogger(AddFeedback.class);

    private IFeedbackService feedbackService;
    private InputFeedbackChecker checker;

    public AddFeedback() {
        this.feedbackService = FeedbackService.getInstance();
        this.checker = new InputFeedbackChecker();
    }

    public AddFeedback(IFeedbackService feedbackService, InputFeedbackChecker checker) {
        this.feedbackService = feedbackService;
        this.checker = checker;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        String locale = (String) request.getSession().getAttribute("locale");
        int applicationId = Integer.parseInt(request.getParameter("applicationId"));
        int mark = Integer.parseInt(request.getParameter("mark"));
        String comment = request.getParameter("comment").trim();

        try {
            checker.checkData(mark, comment);
        } catch (WrongDataException e) {
            page = handleWrongDataException(e, request, locale, mark, comment);
            LOGGER.error("Fail to execute AddFeedback action, wrong data" +
                    ", mark: " + mark +
                    ", comment; " + comment, e);
            return page;
        }

        Feedback feedback = makeFeedback(mark, comment, applicationId);
        page = addFeedback(feedback, request, locale);
        LOGGER.info("Executed AddFeedback action, feedback: " + feedback);

        return page;
}

    private Feedback makeFeedback(int mark, String comment, int applicationId) {
        return new Feedback.Builder()
                .setMark(mark)
                .setComment(comment)
                .setDateTime(LocalDateTime.now())
                .setApplication(new Application.Builder()
                        .setId(applicationId)
                        .build())
                .build();
    }

    private String handleWrongDataException(WrongDataException e, HttpServletRequest request, String locale,
                                            int mark, String comment) {
        setDataAttributes(request, mark, comment);
        switch (e.getMessage()) {
            case "Wrong mark":
                request.setAttribute("wrongMarkMessage",
                        LabelManager.getProperty("message.wrong.mark", locale));
                break;
            case "Wrong comment":
                request.setAttribute("wrongCommentMessage",
                        LabelManager.getProperty("message.wrong.comment", locale));
                break;
        }
        return ConfigurationManager.getProperty("path.page.feedbacks");
    }

    private String addFeedback(Feedback feedback, HttpServletRequest request, String locale) {
        String page = null;

        feedbackService.add(feedback);
        request.setAttribute("successAddFeedbackMessage",
                LabelManager.getProperty("message.success.add.feedback", locale));
        page = ConfigurationManager.getProperty("path.page.feedbacks");

        return page;
    }

    private void setDataAttributes(HttpServletRequest request, int mark, String comment) {
        request.setAttribute("mark", mark);
        request.setAttribute("comment", comment);
    }
}
