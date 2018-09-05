package com.quar17esma.dao.impl;

import com.quar17esma.dao.ConnectionPool;
import com.quar17esma.dao.FeedbackDAO;
import com.quar17esma.entity.Application;
import com.quar17esma.entity.Feedback;
import com.quar17esma.enums.Status;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCFeedbackDAO implements FeedbackDAO {
    private static final Logger LOGGER = Logger.getLogger(JDBCFeedbackDAO.class);

    private static final String FIND_ALL = "SELECT * FROM feedback " +
            "JOIN application ON feedback.application_id = application.id";
    private static final String FIND_BY_ID = "SELECT * FROM feedback " +
            "JOIN application ON feedback.application_id = application.id WHERE feedback.id = ?";
    private static final String UPDATE = "UPDATE feedback SET date_time = ?, comment = ?, mark = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM feedback WHERE id = ?";
    private static final String INSERT = "INSERT INTO feedback (date_time, comment, mark, application_id) VALUES(?, ?, ?, ?) ";
    private static final String FIND_BY_PAGE = "SELECT * FROM feedback ORDER BY feedback.date_time DESC LIMIT ?, ? ";
    private static final String COUNT_ID = "SELECT COUNT(id) FROM feedback ";

    private ConnectionPool connectionPool;

    private JDBCFeedbackDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    private static class Holder {
        private static JDBCFeedbackDAO INSTANCE = new JDBCFeedbackDAO(ConnectionPool.getInstance());
    }

    public static JDBCFeedbackDAO getInstance() {
        return JDBCFeedbackDAO.Holder.INSTANCE;
    }

    @Override
    public List<Feedback> findAll() {
        List<Feedback> feedbackList = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(FIND_ALL)) {
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                Feedback feedback = createFeedback(rs);
                feedbackList.add(feedback);
            }
        } catch (Exception e) {
            LOGGER.error("Fail to find feedbackList", e);
        }

        LOGGER.info("Found all feedbacks");
        return feedbackList;
    }

    @Override
    public Optional<Feedback> findById(long id) {
        Optional<Feedback> feedback = Optional.empty();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(FIND_BY_ID)) {
            query.setLong(1, id);
            ResultSet rs = query.executeQuery();
            if (rs.next()) {
                Feedback fb = createFeedback(rs);
                feedback = Optional.of(fb);
            }
        } catch (Exception e) {
            LOGGER.error("Fail to find meal with id = " + id, e);
        }

        LOGGER.info("Found feedback by id, feedback: " + feedback + ", id: " + id);
        return feedback;
    }

    private Feedback createFeedback(ResultSet rs) throws SQLException {
        return new Feedback.Builder()
                .setId(rs.getLong("feedback.id"))
                .setDateTime(rs.getTimestamp("feedback.date_time").toLocalDateTime())
                .setComment(rs.getString("feedback.comment"))
                .setMark(rs.getInt("feedback.mark"))
                .build();
    }

    private Feedback createFeedbackWithApplication(ResultSet rs) throws SQLException {
        Feedback feedback = createFeedback(rs);

        feedback.setApplication(new Application.Builder()
                .setId(rs.getLong("application.id"))
                .setStatus(Status.valueOf(rs.getString("application.status").toUpperCase()))
                .setPrice(rs.getInt("application.price"))
                .setProduct(rs.getString("application.product"))
                .setRepairType(rs.getString("application.repair_type"))
                .setDeclineReason(rs.getString("application.decline_reason"))
                .setCreateDate(rs.getDate("application.create_date").toLocalDate())
                .setProcessDate(rs.getDate("application.process_date").toLocalDate())
                .setCompleteDate(rs.getDate("application.complete_date").toLocalDate())
                .build()
        );

        return feedback;
    }

    @Override
    public boolean update(Feedback feedback) {
        boolean result = false;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(UPDATE)) {
            query.setTimestamp(1, Timestamp.valueOf(feedback.getDateTime()));
            query.setString(2, feedback.getComment());
            query.setInt(3, feedback.getMark());
            query.setLong(4, feedback.getId());
            query.executeUpdate();
            result = true;
        } catch (Exception e) {
            LOGGER.error("Fail to update feedback with id = " + feedback.getId(), e);
        }

        LOGGER.info("Updated feedback, feedback: " + feedback);
        return result;
    }

    @Override
    public boolean delete(long id) {
        boolean result = false;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(DELETE)) {
            query.setLong(1, id);
            query.executeUpdate();
            result = true;
        } catch (Exception e) {
            LOGGER.error("Fail to delete order with id = " + id, e);
        }

        LOGGER.info("Deleted feedback by id, id: " + id);
        return result;
    }

    @Override
    public long insert(Feedback feedback) {
        long result = -1;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            query.setTimestamp(1, Timestamp.valueOf(feedback.getDateTime()));
            query.setString(2, feedback.getComment());
            query.setInt(3, feedback.getMark());
            query.setLong(4, feedback.getApplication().getId());
            query.executeUpdate();
            ResultSet rsId = query.getGeneratedKeys();
            if (rsId.next()) {
                result = rsId.getLong(1);
                feedback.setId(result);
            }
        } catch (Exception e) {
            LOGGER.error("Fail to insert feedback: " + feedback.toString(), e);
        }

        LOGGER.info("Inserted feedback to DB, feedback: " + feedback);
        return result;
    }

    @Override
    public List<Feedback> findByPage(int page, int itemsOnPage) {
        List<Feedback> feedbacks = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(FIND_BY_PAGE)) {
            query.setInt(1,(page - 1) * itemsOnPage);
            query.setInt(2, itemsOnPage);
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                Feedback feedback = createFeedback(rs);
                feedbacks.add(feedback);
            }
        } catch (Exception e) {
            LOGGER.error("Fail to find feedbacks by page, page = " + page +
                    ", itemsOnPage = " + itemsOnPage, e);
        }

        LOGGER.info("Found feedbacks by page, feedbacks: " + feedbacks +
                ", page: " + page +
                ", itemsOnPage: " + itemsOnPage);
        return feedbacks;
    }

    @Override
    public long countAll() {
        long feedbackCounter = 0;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(COUNT_ID)) {
            ResultSet rs = query.executeQuery();
            if (rs.next()) {
                feedbackCounter = rs.getLong("COUNT(id)");
            }
        } catch (Exception e) {
            LOGGER.error("Fail to count feedbacks", e);
        }
        return feedbackCounter;
    }
}
