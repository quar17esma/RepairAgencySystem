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
    private static final int FEEDBACKS_ON_PAGE = 5;
    private IFeedbackService feedbackService;

    public ShowFeedbacks() {
        this.feedbackService = FeedbackService.getInstance();
    }

    public ShowFeedbacks(IFeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int page = setPageOrGetDefault(request.getParameter("page"));

        List<Feedback> feedbacks = feedbackService.getByPage(page, FEEDBACKS_ON_PAGE);
        long feedbacksQuantity = feedbackService.getAllQuantity();
        int pagesQuantity = countPagesQuantity(feedbacksQuantity);
        request.setAttribute("feedbacks", feedbacks);
        request.setAttribute("pagesQuantity", pagesQuantity);

        return ConfigurationManager.getProperty("path.page.feedbacks");
    }

    private int countPagesQuantity(long applicationQuantity) {
        int pagesQuantity;

        if (applicationQuantity % FEEDBACKS_ON_PAGE != 0) {
            pagesQuantity = (int) (applicationQuantity / FEEDBACKS_ON_PAGE + 1);
        } else {
            pagesQuantity = (int) (applicationQuantity / FEEDBACKS_ON_PAGE);
        }

        return pagesQuantity;
    }

    private int setPageOrGetDefault(String pageString) {
        int page;

        if (pageString != null) {
            page = Integer.parseInt(pageString);
        } else {
            page = DEFAULT_PAGE;
        }

        return page;
    }
}
