package com.quar17esma.controller.action.impl;

import com.quar17esma.controller.action.Action;
import com.quar17esma.controller.checker.InputFeedbackChecker;
import com.quar17esma.controller.manager.ConfigurationManager;
import com.quar17esma.controller.manager.LabelManager;
import com.quar17esma.entity.Application;
import com.quar17esma.entity.Feedback;
import com.quar17esma.entity.User;
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
        boolean isDataCorrect = checker.isInputDataCorrect(mark, comment);

        if (isDataCorrect) {
            Feedback feedback = makeFeedback(mark, comment, applicationId);
            page = addFeedback(feedback, request, locale);
            LOGGER.info("Executed AddFeedback action, feedback: " + feedback);
        } else {
            setDataAttributes(request, mark, comment);
            request.setAttribute("errorAddFeedbackMessage",
                    LabelManager.getProperty("message.error.wrong.data", locale));
            page = ConfigurationManager.getProperty("path.page.feedbacks");
            LOGGER.info("Fail to execute AddFeedback action, wrong data");
        }

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
