package com.quar17esma.service.impl;

import com.quar17esma.dao.DaoFactory;
import com.quar17esma.dao.FeedbackDAO;
import com.quar17esma.entity.Feedback;
import com.quar17esma.service.IFeedbackService;
import org.apache.log4j.Logger;

import java.util.List;

public class FeedbackService extends Service<Feedback> implements IFeedbackService {
    private FeedbackDAO feedbackDAO;

    private FeedbackService(DaoFactory factory) {
        super(factory);
        this.feedbackDAO = factory.createFeedbackDAO();
        dao = this.feedbackDAO;
        logger = Logger.getLogger(FeedbackService.class);
    }

    private static class Holder {
        private static FeedbackService INSTANCE = new FeedbackService(DaoFactory.getInstance());
    }

    public static FeedbackService getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    protected void setId(Feedback item, long id) {
        item.setId(id);
    }

    public List<Feedback> getByPage(long page, int feedbacksOnPage) {
        List<Feedback> feedbacks = feedbackDAO.findByPage(page, feedbacksOnPage);

        logger.info("Got feedbacks by page, feedbacks: " + feedbacks +
                ", page: " + page +
                ", feedbacksOnPage: " + feedbacksOnPage);
        return feedbacks;
    }

    public long getAllQuantity() {
        return feedbackDAO.countAll();
    }
}
