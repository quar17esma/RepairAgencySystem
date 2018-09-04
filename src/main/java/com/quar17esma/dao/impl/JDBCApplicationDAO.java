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
import java.util.Optional;

public class JDBCApplicationDAO implements ApplicationDAO {
    private static final Logger LOGGER = Logger.getLogger(JDBCApplicationDAO.class);

    private static final String FIND_ALL = "SELECT * FROM application ";
    private static final String FIND_BY_ID = "SELECT * FROM application WHERE application.id = ? ";
    private static final String FIND_ALL_BY_USER_ID = "SELECT * FROM application " +
            "JOIN user ON application.user_id = user.id " +
            "JOIN feedback ON application.id = feedback.application_id " +
            "WHERE application.user_id = ? ";
    private static final String UPDATE = "UPDATE application " +
            "SET status = ?, price = ?, product = ?, repair_type = ?, " +
            "create_date = ?, process_date = ?, complete_date = ?, decline_reason = ? " +
            "WHERE id = ?";
    private static final String DELETE = "DELETE FROM application WHERE id = ? ";
    private static final String INSERT = "INSERT INTO application " +
            "(user_id, status, price, product, repair_type, create_date, process_date, complete_date, decline_reason) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?) ";
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

    private ConnectionPool connectionPool;

    private JDBCApplicationDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    private static class Holder {
        private static JDBCApplicationDAO INSTANCE = new JDBCApplicationDAO(ConnectionPool.getInstance());
    }

    public static JDBCApplicationDAO getInstance() {
        return JDBCApplicationDAO.Holder.INSTANCE;
    }

    @Override
    public List<Application> findAll() {
        List<Application> applications = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(FIND_ALL)) {
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                Application application = createApplication(rs);
                applications.add(application);
            }
        } catch (Exception e) {
            LOGGER.error("Fail to find applications", e);
        }

        return applications;
    }

    @Override
    public Optional<Application> findById(long id) {
        Optional<Application> result = Optional.empty();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(FIND_BY_ID)) {
            query.setLong(1, id);
            ResultSet rs = query.executeQuery();
            if (rs.next()) {
                Application application = createApplication(rs);
                result = Optional.of(application);
            }
        } catch (Exception e) {
            LOGGER.error("Fail to find application with id = " + id, e);
        }

        return result;
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
        } catch (Exception e) {
            LOGGER.error("Fail to find applications by user id: " + userId, e);
        }

        return applications;
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

    private Application createApplicationWithFeedback(ResultSet rs) throws SQLException {
        Application application = createApplication(rs);

        if (rs.getString("feedback.comment") != null) {
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

    @Override
    public boolean update(Application application) {
        boolean result = false;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(UPDATE)) {
            query.setString(1, application.getStatus().name());
            query.setInt(2, application.getPrice());
            query.setString(3, application.getProduct());
            query.setString(4, application.getRepairType());
            query.setDate(5, Date.valueOf(application.getCreateDate()));
            Date processDate = (application.getProcessDate() != null) ? Date.valueOf(application.getProcessDate()) : null;
            query.setDate(6, processDate);
            Date completeDate = (application.getCompleteDate() != null) ? Date.valueOf(application.getCompleteDate()) : null;
            query.setDate(7, completeDate);
            query.setString(8, application.getDeclineReason());
            query.setLong(9, application.getId());
            query.executeUpdate();
            result = true;
        } catch (Exception e) {
            LOGGER.error("Fail to update application with id = " + application.getId(), e);
        }

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
            LOGGER.error("Fail to delete application with id = " + id, e);
        }

        return result;
    }

    @Override
    public long insert(Application application) {
        long result = -1;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            query.setLong(1, application.getUser().getId());
            query.setString(2, application.getStatus().name());
            query.setInt(3, application.getPrice());
            query.setString(4, application.getProduct());
            query.setString(5, application.getRepairType());
            query.setDate(6, Date.valueOf(application.getCreateDate()));
            Date processDate = (application.getProcessDate() != null) ? Date.valueOf(application.getProcessDate()) : null;
            query.setDate(7, processDate);
            Date completeDate = (application.getCompleteDate() != null) ? Date.valueOf(application.getCompleteDate()) : null;
            query.setDate(8, completeDate);
            query.setString(9, application.getDeclineReason());
            query.executeUpdate();
            ResultSet rsId = query.getGeneratedKeys();
            if (rsId.next()) {
                result = rsId.getLong(1);
                application.setId(result);
            }
        } catch (Exception e) {
            LOGGER.error("Fail to insert application: " + application.toString(), e);
        }

        return result;
    }

    @Override
    public List<Application> findByPage(int page, int applicationsOnPage) {
        List<Application> applications = new ArrayList<>();

        int offset = (page - 1) * applicationsOnPage;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(FIND_BY_PAGE)) {
            query.setInt(1, offset);
            query.setInt(2, applicationsOnPage);
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                Application application = createApplication(rs);
                applications.add(application);
            }
        } catch (Exception e) {
            LOGGER.error("Fail to find applications by Page, page = " + page +
                    ", applicationsOnPage = " + applicationsOnPage, e);
        }

        return applications;
    }

    @Override
    public long countAll() {
        long counter = 0;

        try {
            counter = count(COUNT_ID);
            return counter;
        } catch (SQLException e) {
            LOGGER.error("Fail to count all applications", e);
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
    public List<Application> findAcceptedByPage(int page, int applicationsOnPage) {
        List<Application> applications = new ArrayList<>();

        int offset = (page - 1) * applicationsOnPage;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(FIND_ACCEPTED_BY_PAGE)) {
            query.setInt(1, offset);
            query.setInt(2, applicationsOnPage);
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                Application application = createApplication(rs);
                applications.add(application);
            }
        } catch (Exception e) {
            LOGGER.error("Fail to find accepted applications by Page, page = " + page +
                    ", applicationsOnPage = " + applicationsOnPage, e);
        }

        return applications;
    }

    @Override
    public long countAccepted() {
        long counter = 0;

        try {
            return count(COUNT_ACCEPTED_ID);
        } catch (SQLException e) {
            LOGGER.error("Fail to count accepted applications", e);
        }

        return counter;
    }

    @Override
    public List<Application> findByUserIdByPage(long userId, int page, int applicationsOnPage) {
        List<Application> applications = new ArrayList<>();

        int offset = (page - 1) * applicationsOnPage;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(FIND_BY_USER_ID_BY_PAGE)) {
            query.setLong(1, userId);
            query.setInt(2, offset);
            query.setInt(3, applicationsOnPage);
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                Application application = createApplicationWithFeedback(rs);
                applications.add(application);
            }
        } catch (Exception e) {
            LOGGER.error("Fail to find applications by user id by Page, page = " + page +
                    ", applicationsOnPage = " + applicationsOnPage, e);
        }

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
        } catch (Exception e) {
            LOGGER.error("Fail to count by user id applications", e);
        }
        return applicationCounter;
    }
}
