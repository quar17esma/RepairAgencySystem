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
        return feedbackDAO.findAll();
    }

    public Feedback getById(long id) {
        return feedbackDAO.findById(id).get();
    }

    public void update(Feedback feedback) {
        feedbackDAO.update(feedback);
    }

    public void delete(long id) {
        feedbackDAO.delete(id);
    }

    public void add(Feedback feedback) {
        feedbackDAO.insert(feedback);
    }

    public List<Feedback> getByPage(int page, int feedbacksOnPage) {
        return feedbackDAO.findByPage(page, feedbacksOnPage);
    }

    public long getAllQuantity() {
        return feedbackDAO.countAll();
    }
}
