package com.quar17esma.service.impl;

import com.quar17esma.dao.ApplicationDAO;
import com.quar17esma.dao.ConnectionPool;
import com.quar17esma.dao.DaoFactory;
import com.quar17esma.entity.Application;
import com.quar17esma.service.IApplicationService;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.List;

public class ApplicationService extends Service implements IApplicationService {
    private static final Logger LOGGER = Logger.getLogger(ApplicationService.class);

    private ApplicationService(DaoFactory factory, ConnectionPool connectionPool) {
        super(factory, connectionPool);
    }

    private static class Holder {
        private static ApplicationService INSTANCE = new ApplicationService(DaoFactory.getInstance(), ConnectionPool.getInstance());
    }

    public static ApplicationService getInstance() {
        return Holder.INSTANCE;
    }

    public List<Application> getByUserId(long userId) {
        List<Application> applications = null;

        try (Connection connection = connectionPool.getConnection();
             ApplicationDAO applicationDAO = factory.createApplicationDAO(connection)) {
            connection.setAutoCommit(true);
            applications = applicationDAO.findAllByUserId(userId);
        } catch (Exception e) {
            LOGGER.error("Fail to get applications with userId = " + userId, e);
            throw new RuntimeException(e);
        }

        return applications;
    }

    @Override
    public List<Application> getAll() {
        return null;
    }

    @Override
    public Application getById(long id) {
        return null;
    }

    @Override
    public void update(Application item) {

    }

    @Override
    public void delete(long id) {
        try (Connection connection = connectionPool.getConnection();
             ApplicationDAO applicationDAO = factory.createApplicationDAO(connection)) {
            connection.setAutoCommit(true);
            applicationDAO.delete(id);
        } catch (Exception e) {
            LOGGER.error("Fail to delete application with id: " + id, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(Application application) {
        try (Connection connection = connectionPool.getConnection();
             ApplicationDAO applicationDAO = factory.createApplicationDAO(connection)) {
            connection.setAutoCommit(true);
            applicationDAO.insert(application);
        } catch (Exception e) {
            LOGGER.error("Fail to add application: " + application, e);
            throw new RuntimeException(e);
        }
    }
}
