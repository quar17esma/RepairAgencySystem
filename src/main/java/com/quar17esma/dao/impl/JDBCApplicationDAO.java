package com.quar17esma.dao.impl;

import com.quar17esma.dao.ApplicationDAO;
import com.quar17esma.dao.ConnectionPool;
import com.quar17esma.entity.Application;
import com.quar17esma.entity.Feedback;
import com.quar17esma.enums.Status;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JDBCApplicationDAO extends JDBCGenericDAO<Application> implements ApplicationDAO {
    private static final String FIND_ALL_BY_USER_ID = "SELECT * FROM application " +
            "JOIN user ON application.user_id = user.id " +
            "JOIN feedback ON application.id = feedback.application_id " +
            "WHERE application.user_id = ? ";
    private static final String FIND_BY_PAGE = "SELECT * FROM application " +
            "ORDER BY application.create_date DESC " +
            "LIMIT ?, ? ";
    private static final String FIND_ACCEPTED_BY_PAGE = "SELECT * FROM application " +
            "WHERE application.status = 'ACCEPTED' " +
            "ORDER BY application.create_date DESC " +
            "LIMIT ?, ? ";
    private static final String FIND_BY_USER_ID_BY_PAGE = "SELECT * FROM application " +
            "LEFT JOIN feedback ON application.id = feedback.application_id " +
            "WHERE application.user_id = ? " +
            "ORDER BY application.create_date DESC " +
            "LIMIT ?, ? ";
    private static final String COUNT_ID = "SELECT COUNT(id) FROM application ";
    private static final String COUNT_ACCEPTED_ID = "SELECT COUNT(id) FROM application " +
            "WHERE application.status = 'ACCEPTED' ";
    private static final String COUNT_BY_USER_ID = "SELECT COUNT(id) FROM application " +
            "WHERE application.user_id = ? ";

    private JDBCApplicationDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        logger = Logger.getLogger(JDBCApplicationDAO.class);

        findAll = "SELECT * FROM application ";
        findById = "SELECT * FROM application WHERE application.id = ? ";
        update = "UPDATE application " +
                "SET status = ?, price = ?, product = ?, repair_type = ?, " +
                "create_date = ?, process_date = ?, complete_date = ?, decline_reason = ? " +
                "WHERE id = ?";
        delete = "DELETE FROM application WHERE id = ? ";
        insert = "INSERT INTO application " +
                "(user_id, status, price, product, repair_type, create_date, process_date, complete_date, decline_reason) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?) ";
    }

    private static class Holder {
        private static JDBCApplicationDAO INSTANCE = new JDBCApplicationDAO(ConnectionPool.getInstance());
    }

    public static JDBCApplicationDAO getInstance() {
        return JDBCApplicationDAO.Holder.INSTANCE;
    }

    @Override
    protected Application createItem(ResultSet rs) throws SQLException {
        return createApplicationWithFeedback(rs);
    }

    private Application createApplicationWithFeedback(ResultSet rs) throws SQLException {
        Application application = createApplication(rs);

        if (hasColumn(rs, "feedback.id") && rs.getString("feedback.id") != null) {
            application.setFeedback(new Feedback.Builder()
                    .setId(rs.getLong("feedback.id"))
                    .setMark(rs.getInt("feedback.mark"))
                    .setComment(rs.getString("feedback.comment"))
                    .setDateTime(rs.getTimestamp("feedback.date_time").toLocalDateTime())
                    .build()
            );
        }

        return application;
    }

    private Application createApplication(ResultSet rs) throws SQLException {
        LocalDate processDate = (rs.getDate("application.process_date") != null) ?
                rs.getDate("application.process_date").toLocalDate() : null;
        LocalDate completeDate = (rs.getDate("application.complete_date") != null) ?
                rs.getDate("application.complete_date").toLocalDate() : null;
        return new Application.Builder()
                .setId(rs.getLong("application.id"))
                .setStatus(Status.valueOf(rs.getString("application.status").toUpperCase()))
                .setProduct(rs.getString("application.product"))
                .setPrice(rs.getInt("application.price"))
                .setRepairType(rs.getString("application.repair_type"))
                .setCreateDate(rs.getDate("application.create_date").toLocalDate())
                .setProcessDate(processDate)
                .setCompleteDate(completeDate)
                .setDeclineReason(rs.getString("application.decline_reason"))
                .build();
    }

    @Override
    protected void setUpdateQueryParams(PreparedStatement query, Application item) throws SQLException {
        query.setString(1, item.getStatus().name());
        query.setInt(2, item.getPrice());
        query.setString(3, item.getProduct());
        query.setString(4, item.getRepairType());
        query.setDate(5, Date.valueOf(item.getCreateDate()));
        Date processDate = (item.getProcessDate() != null) ? Date.valueOf(item.getProcessDate()) : null;
        query.setDate(6, processDate);
        Date completeDate = (item.getCompleteDate() != null) ? Date.valueOf(item.getCompleteDate()) : null;
        query.setDate(7, completeDate);
        query.setString(8, item.getDeclineReason());
        query.setLong(9, item.getId());
    }

    @Override
    protected void setInsertQueryParams(PreparedStatement query, Application item) throws SQLException {
        query.setLong(1, item.getUser().getId());
        query.setString(2, item.getStatus().name());
        query.setInt(3, item.getPrice());
        query.setString(4, item.getProduct());
        query.setString(5, item.getRepairType());
        query.setDate(6, Date.valueOf(item.getCreateDate()));
        Date processDate = (item.getProcessDate() != null) ? Date.valueOf(item.getProcessDate()) : null;
        query.setDate(7, processDate);
        Date completeDate = (item.getCompleteDate() != null) ? Date.valueOf(item.getCompleteDate()) : null;
        query.setDate(8, completeDate);
        query.setString(9, item.getDeclineReason());
    }

    @Override
    public List<Application> findAllByUserId(long userId) {
        List<Application> applications = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(FIND_ALL_BY_USER_ID)) {
            query.setLong(1, userId);
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                Application application = createApplicationWithFeedback(rs);
                applications.add(application);
            }
        } catch (SQLException e) {
            logger.error("Fail to find applications by user id: " + userId, e);
        }

        logger.info("Found applications by user id, applications: " + applications +
                ", user id: " + userId);
        return applications;
    }

    @Override
    public List<Application> findAllByPage(long page, int applicationsOnPage) {
        List<Application> applications = new ArrayList<>();

        try {
            applications = findByPage(page, applicationsOnPage, FIND_BY_PAGE);
        } catch (SQLException e) {
            logger.error("Fail to find applications by Page, page = " + page +
                    ", applicationsOnPage = " + applicationsOnPage, e);
        }

        logger.info("Found applications by page, applications: " + applications +
                ", page: " + page +
                ", applicationsOnPage: " + applicationsOnPage);
        return applications;
    }

    @Override
    public long countAll() {
        long counter = 0;

        try {
            counter = count(COUNT_ID);
            return counter;
        } catch (SQLException e) {
            logger.error("Fail to count all applications", e);
        }

        return counter;
    }

    private long count(String sql) throws SQLException {
        long applicationCounter = 0;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(sql)) {
            ResultSet rs = query.executeQuery();
            if (rs.next()) {
                applicationCounter = rs.getLong("COUNT(id)");
            }
        }
        return applicationCounter;
    }

    @Override
    public List<Application> findAcceptedByPage(long page, int itemsOnPage) {
        List<Application> applications = new ArrayList<>();

        try {
            applications = findByPage(page, itemsOnPage, FIND_ACCEPTED_BY_PAGE);
        } catch (SQLException e) {
            logger.error("Fail to find accepted applications by Page, page = " + page +
                    ", itemsOnPage = " + itemsOnPage, e);
        }

        logger.info("Found accepted applications by page, applications: " + applications +
                ", page: " + page +
                ", itemsOnPage: " + itemsOnPage);
        return applications;
    }

    private List<Application> findByPage(long page, int itemsOnPage, String sql) throws SQLException {
        List<Application> applications = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(sql)) {
            query.setLong(1, (page - 1) * itemsOnPage);
            query.setInt(2, itemsOnPage);
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                Application application = createApplication(rs);
                applications.add(application);
            }
        }

        return applications;
    }

    @Override
    public long countAccepted() {
        long counter = 0;

        try {
            return count(COUNT_ACCEPTED_ID);
        } catch (SQLException e) {
            logger.error("Fail to count accepted applications", e);
        }

        return counter;
    }

    @Override
    public List<Application> findByUserIdByPage(long userId, long page, int itemsOnPage) {
        List<Application> applications = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(FIND_BY_USER_ID_BY_PAGE)) {
            query.setLong(1, userId);
            query.setLong(2, (page - 1) * itemsOnPage);
            query.setInt(3, itemsOnPage);
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                Application application = createApplicationWithFeedback(rs);
                applications.add(application);
            }
        } catch (SQLException e) {
            logger.error("Fail to find applications by user id by Page, page = " + page +
                    ", itemsOnPage = " + itemsOnPage, e);
        }

        logger.info("Found applications by user id by page, applications: " + applications +
                ", user id: " + userId +
                ", page: " + page +
                ", itemsOnPage: " + itemsOnPage);
        return applications;
    }

    @Override
    public long countAllByUserId(long userId) {
        long applicationCounter = 0;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(COUNT_BY_USER_ID)) {
            query.setLong(1, userId);
            ResultSet rs = query.executeQuery();
            if (rs.next()) {
                applicationCounter = rs.getLong("COUNT(id)");
            }
        } catch (SQLException e) {
            logger.error("Fail to count by user id applications", e);
        }
        return applicationCounter;
    }

    private boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columns = rsmd.getColumnCount();
        for (int x = 1; x <= columns; x++) {
            if (columnName.equals(rsmd.getColumnName(x))) {
                return true;
            }
        }
        return false;
    }
}
