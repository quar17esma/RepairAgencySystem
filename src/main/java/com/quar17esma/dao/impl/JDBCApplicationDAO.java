package com.quar17esma.dao.impl;

import com.quar17esma.dao.ApplicationDAO;
import com.quar17esma.entity.Application;
import com.quar17esma.enums.Status;
import org.apache.log4j.Logger;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class JDBCApplicationDAO implements ApplicationDAO {
    private static final Logger LOGGER = Logger.getLogger(JDBCApplicationDAO.class);

    private static final String FIND_ALL = "SELECT * FROM application ";
    private static final String FIND_BY_ID = "SELECT * FROM application WHERE application.id = ? ";
    private static final String FIND_ALL_BY_USER_ID = "SELECT * FROM application " +
            "JOIN user ON application.user_id = user.id WHERE application.user_id = ? ";
    private static final String UPDATE = "UPDATE application " +
            "SET status = ?, price = ?, product = ?, repair_type = ?, " +
            "create_date = ?, process_date = ?, complete_date = ?, decline_reason = ? " +
            "WHERE id = ?";
    private static final String DELETE = "DELETE FROM application WHERE id = ? ";
    private static final String INSERT = "INSERT INTO application " +
            "(user_id, status, price, product, repair_type, create_date, process_date, complete_date, decline_reason) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?) ";
    private static final String FIND_BY_PAGE = "SELECT * FROM application ORDER BY application.create_date DESC LIMIT ?, ? ";
    private static final String COUNT_ID = "SELECT COUNT(id) FROM application ";

    private Connection connection;

    public JDBCApplicationDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Application> findAll() {
        List<Application> applications = new ArrayList<>();

        try (PreparedStatement query = connection.prepareStatement(FIND_ALL)) {
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                Application application = createApplication(rs);
                applications.add(application);
            }
        } catch (Exception e) {
            LOGGER.error("Fail to find applications", e);
            throw new RuntimeException(e);
        }

        return applications;
    }

    @Override
    public Optional<Application> findById(long id) {
        Optional<Application> result = Optional.empty();

        try (PreparedStatement query = connection.prepareStatement(FIND_BY_ID)) {
            query.setLong(1, id);
            ResultSet rs = query.executeQuery();
            if (rs.next()) {
                Application application = createApplication(rs);
                result = Optional.of(application);
            }
        } catch (Exception e) {
            LOGGER.error("Fail to find application with id = " + id, e);
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public List<Application> findAllByUserId(long userId) {
        List<Application> applications = new ArrayList<>();

        try (PreparedStatement query = connection.prepareStatement(FIND_ALL_BY_USER_ID)) {
            query.setLong(1, userId);
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                Application application = createApplication(rs);
                applications.add(application);
            }
        } catch (Exception e) {
            LOGGER.error("Fail to find applications by user id: " + userId, e);
            throw new RuntimeException(e);
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

    @Override
    public boolean update(Application application) {
        boolean result = false;

        try (PreparedStatement query = connection.prepareStatement(UPDATE)) {
            query.setString(1, application.getStatus().name());
            query.setInt(2, application.getPrice());
            query.setString(3, application.getProduct());
            query.setString(4, application.getRepairType());
            query.setDate(5, Date.valueOf(application.getCreateDate()));
            query.setDate(6, Date.valueOf(application.getProcessDate()));
            query.setDate(7, Date.valueOf(application.getCompleteDate()));
            query.setString(8, application.getDeclineReason());
            query.executeUpdate();
            result = true;
        } catch (Exception e) {
            LOGGER.error("Fail to update application with id = " + application.getId(), e);
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public boolean delete(long id) {
        boolean result = false;

        try (PreparedStatement query = connection.prepareStatement(DELETE)) {
            query.setLong(1, id);
            query.executeUpdate();
            result = true;
        } catch (Exception e) {
            LOGGER.error("Fail to delete application with id = " + id, e);
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public long insert(Application application) {
        long result = -1;

        try (PreparedStatement query = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
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
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public List<Application> findByPage(int page, int applicationsOnPage) {
        List<Application> applications = new ArrayList<>();

        int offset = (page - 1) * applicationsOnPage;

        try (PreparedStatement query = connection.prepareStatement(FIND_BY_PAGE)) {
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
            throw new RuntimeException(e);
        }

        return applications;
    }

    @Override
    public long countAll() {
        long applicationCounter = 0;
        try (PreparedStatement query = connection.prepareStatement(COUNT_ID)) {
            ResultSet rs = query.executeQuery();
            if (rs.next()) {
                applicationCounter = rs.getLong("COUNT(id)");
            }
        } catch (Exception e) {
            LOGGER.error("Fail to count applications", e);
            throw new RuntimeException(e);
        }
        return applicationCounter;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
