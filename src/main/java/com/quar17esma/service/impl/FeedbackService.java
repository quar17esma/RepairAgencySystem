package com.quar17esma.service.impl;

import com.quar17esma.dao.ConnectionPool;
import com.quar17esma.dao.DaoFactory;
import com.quar17esma.dao.FeedbackDAO;
import com.quar17esma.entity.Feedback;
import com.quar17esma.service.IFeedbackService;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.List;

public class FeedbackService extends Service implements IFeedbackService {
    private static final Logger LOGGER = Logger.getLogger(FeedbackService.class);

    private FeedbackService(DaoFactory factory, ConnectionPool connectionPool) {
        super(factory, connectionPool);
    }

    private static class Holder {
        private static FeedbackService INSTANCE = new FeedbackService(DaoFactory.getInstance(), ConnectionPool.getInstance());
    }

    public static FeedbackService getInstance() {
        return Holder.INSTANCE;
    }

    public List<Feedback> getAll() {
        List<Feedback> feedbackList = null;

        try (Connection connection = connectionPool.getConnection();
             FeedbackDAO feedbackDAO = factory.createFeedbackDAO(connection)) {
            connection.setAutoCommit(true);
            feedbackList = feedbackDAO.findAll();
        } catch (Exception e) {
            LOGGER.error("Fail to get all feedbacks", e);
            throw new RuntimeException(e);
        }

        return feedbackList;
    }

    public List<Feedback> getByPage(int page, int feedbacksOnPage) {
        List<Feedback> feedbackList = null;

        try (Connection connection = connectionPool.getConnection();
             FeedbackDAO feedbackDAO = factory.createFeedbackDAO(connection)) {
            connection.setAutoCommit(true);
            feedbackList = feedbackDAO.findByPage(page, feedbacksOnPage);
        } catch (Exception e) {
            LOGGER.error("Fail to get all feedbacks by page, page = " + page +
                    ", feedbacksOnPage = " + feedbacksOnPage, e);
            throw new RuntimeException(e);
        }

        return feedbackList;
    }

    public long getAllQuantity() {
        long foodCounter;

        try (Connection connection = connectionPool.getConnection();
             FeedbackDAO feedbackDAO = factory.createFeedbackDAO(connection)) {
            connection.setAutoCommit(true);
            foodCounter = feedbackDAO.countAll();
        } catch (Exception e) {
            LOGGER.error("Fail to get all feedbacks quantity", e);
            throw new RuntimeException(e);
        }

        return foodCounter;
    }

    public Feedback getById(long id) {
        Feedback feedback = null;

        try (Connection connection = connectionPool.getConnection();
             FeedbackDAO feedbackDAO = factory.createFeedbackDAO(connection)) {
            connection.setAutoCommit(true);
            feedback = feedbackDAO.findById(id).get();
        } catch (Exception e) {
            LOGGER.error("Fail to find feedback with id = " + id, e);
            throw new RuntimeException(e);
        }

        return feedback;
    }

    public void deleteById(long id) {
        try (Connection connection = connectionPool.getConnection();
             FeedbackDAO feedbackDAO = factory.createFeedbackDAO(connection)) {
            connection.setAutoCommit(true);
            feedbackDAO.delete(id);
        } catch (Exception e) {
            LOGGER.error("Fail to delete feedback with id = " + id, e);
            throw new RuntimeException(e);
        }
    }

    public void add(Feedback feedback) {
        try (Connection connection = connectionPool.getConnection();
             FeedbackDAO feedbackDAO = factory.createFeedbackDAO(connection)) {
            connection.setAutoCommit(true);
            feedbackDAO.insert(feedback);
        } catch (Exception e) {
            LOGGER.error("Fail to add feedback: " + feedback, e);
            throw new RuntimeException(e);
        }
    }

    public void update(Feedback feedback) {
        try (Connection connection = connectionPool.getConnection();
             FeedbackDAO feedbackDAO = factory.createFeedbackDAO(connection)) {
            connection.setAutoCommit(true);
            feedbackDAO.update(feedback);
        } catch (Exception e) {
            LOGGER.error("Fail to update feedback with id = " + feedback.getId(), e);
            throw new RuntimeException(e);
        }
    }
}
