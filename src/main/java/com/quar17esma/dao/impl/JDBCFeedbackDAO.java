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

public class JDBCFeedbackDAO extends JDBCGenericDAO<Feedback> implements FeedbackDAO {
    private static final String FIND_BY_PAGE = "SELECT * FROM feedback ORDER BY feedback.date_time DESC LIMIT ?, ? ";
    private static final String COUNT_ID = "SELECT COUNT(id) FROM feedback ";

    private JDBCFeedbackDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        logger = Logger.getLogger(JDBCFeedbackDAO.class);
        findAll = "SELECT * FROM feedback " +
                "JOIN application ON feedback.application_id = application.id";
        findById = "SELECT * FROM feedback " +
                "JOIN application ON feedback.application_id = application.id WHERE feedback.id = ?";
        update = "UPDATE feedback SET date_time = ?, comment = ?, mark = ? WHERE id = ?";
        delete = "DELETE FROM feedback WHERE id = ?";
        insert = "INSERT INTO feedback (date_time, comment, mark, application_id) VALUES(?, ?, ?, ?) ";
    }

    private static class Holder {
        private static JDBCFeedbackDAO INSTANCE = new JDBCFeedbackDAO(ConnectionPool.getInstance());
    }

    public static JDBCFeedbackDAO getInstance() {
        return JDBCFeedbackDAO.Holder.INSTANCE;
    }

    @Override
    protected Feedback createItem(ResultSet rs) throws SQLException {
        return new Feedback.Builder()
                .setId(rs.getLong("feedback.id"))
                .setDateTime(rs.getTimestamp("feedback.date_time").toLocalDateTime())
                .setComment(rs.getString("feedback.comment"))
                .setMark(rs.getInt("feedback.mark"))
                .build();
    }

    @Override
    protected void setUpdateQueryParams(PreparedStatement query, Feedback item) throws SQLException {
        query.setTimestamp(1, Timestamp.valueOf(item.getDateTime()));
        query.setString(2, item.getComment());
        query.setInt(3, item.getMark());
        query.setLong(4, item.getId());
    }

    @Override
    protected void setInsertQueryParams(PreparedStatement query, Feedback item) throws SQLException {
        query.setTimestamp(1, Timestamp.valueOf(item.getDateTime()));
        query.setString(2, item.getComment());
        query.setInt(3, item.getMark());
        query.setLong(4, item.getApplication().getId());
    }

    private Feedback createFeedbackWithApplication(ResultSet rs) throws SQLException {
        Feedback feedback = createItem(rs);

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
    public List<Feedback> findByPage(long page, int itemsOnPage) {
        List<Feedback> feedbacks = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(FIND_BY_PAGE)) {
            query.setLong(1, (page - 1) * itemsOnPage);
            query.setInt(2, itemsOnPage);
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                Feedback feedback = createItem(rs);
                feedbacks.add(feedback);
            }
        } catch (SQLException e) {
            logger.error("Fail to find feedbacks by page, page = " + page +
                    ", itemsOnPage = " + itemsOnPage, e);
        }

        logger.info("Found feedbacks by page, feedbacks: " + feedbacks +
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
        } catch (SQLException e) {
            logger.error("Fail to count feedbacks", e);
        }
        return feedbackCounter;
    }
}
