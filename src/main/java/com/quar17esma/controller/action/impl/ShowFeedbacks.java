package com.quar17esma.controller.action.impl;

import com.quar17esma.controller.action.Action;
import com.quar17esma.controller.manager.ConfigurationManager;
import com.quar17esma.entity.Feedback;
import com.quar17esma.service.IFeedbackService;
import com.quar17esma.service.impl.FeedbackService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowFeedbacks implements Action {
    private static final int DEFAULT_PAGE = 1;
    private static final int ITEMS_ON_PAGE = 5;
    private IFeedbackService feedbackService;

    public ShowFeedbacks() {
        this.feedbackService = FeedbackService.getInstance();
    }

    public ShowFeedbacks(IFeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        long pageNumber = setPageNumberOrDefault(request.getParameter("page"));

        List<Feedback> feedbacks = feedbackService.getByPage(pageNumber, ITEMS_ON_PAGE);
        long feedbacksQuantity = feedbackService.getAllQuantity();
        long pagesQuantity = countPagesQuantity(feedbacksQuantity);
        request.setAttribute("feedbacks", feedbacks);
        request.setAttribute("pagesQuantity", pagesQuantity);

        return ConfigurationManager.getProperty("path.page.feedbacks");
    }

    private long countPagesQuantity(long itemQuantity) {
        return (itemQuantity % ITEMS_ON_PAGE != 0)
                ? (itemQuantity / ITEMS_ON_PAGE + 1)
                : (itemQuantity / ITEMS_ON_PAGE);
    }

    private long setPageNumberOrDefault(String pageString) {
        return (pageString != null) ? Integer.parseInt(pageString) : DEFAULT_PAGE;
    }
}
