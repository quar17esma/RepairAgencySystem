package com.quar17esma.service.impl;

import com.quar17esma.dao.DaoFactory;
import com.quar17esma.dao.FeedbackDAO;
import com.quar17esma.entity.Feedback;
import com.quar17esma.service.IFeedbackService;
import org.apache.log4j.Logger;

import java.util.List;

public class FeedbackService extends Service implements IFeedbackService {
    private static final Logger LOGGER = Logger.getLogger(FeedbackService.class);

    private FeedbackDAO feedbackDAO;

    private FeedbackService(DaoFactory factory) {
        super(factory);
        this.feedbackDAO = factory.createFeedbackDAO();
    }

    private static class Holder {
        private static FeedbackService INSTANCE = new FeedbackService(DaoFactory.getInstance());
    }

    public static FeedbackService getInstance() {
        return Holder.INSTANCE;
    }

    public List<Feedback> getAll() {
        List<Feedback> feedbacks = feedbackDAO.findAll();

        LOGGER.info("Got all feedbacks");
        return feedbacks;
    }

    public Feedback getById(long id) {
        Feedback feedback = feedbackDAO.findById(id).get();

        LOGGER.info("Got feedback by id, feedback: " + feedback + ", id: " + id);
        return feedback;
    }

    public void update(Feedback feedback) {
        feedbackDAO.update(feedback);

        LOGGER.info("Updated feedback, feedback: " + feedback);
    }

    public void delete(long id) {
        feedbackDAO.delete(id);

        LOGGER.info("Deleted feedback by id, id: " + id);
    }

    public void add(Feedback feedback) {
        feedbackDAO.insert(feedback);

        LOGGER.info("Added feedback, feedback: " + feedback);
    }

    public List<Feedback> getByPage(long page, int feedbacksOnPage) {
        List<Feedback> feedbacks = feedbackDAO.findByPage(page, feedbacksOnPage);

        LOGGER.info("Got feedbacks by page, feedbacks: " + feedbacks +
                ", page: " + page +
                ", feedbacksOnPage: " + feedbacksOnPage);
        return feedbacks;
    }

    public long getAllQuantity() {
        return feedbackDAO.countAll();
    }
}
