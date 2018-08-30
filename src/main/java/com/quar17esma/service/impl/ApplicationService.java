package com.quar17esma.service.impl;

import com.quar17esma.dao.ApplicationDAO;
import com.quar17esma.dao.DaoFactory;
import com.quar17esma.entity.Application;
import com.quar17esma.service.IApplicationService;
import org.apache.log4j.Logger;

import java.util.List;

public class ApplicationService extends Service implements IApplicationService {
    private static final Logger LOGGER = Logger.getLogger(ApplicationService.class);

    private ApplicationService(DaoFactory factory) {
        super(factory);
    }

    private static class Holder {
        private static ApplicationService INSTANCE = new ApplicationService(DaoFactory.getInstance());
    }

    public static ApplicationService getInstance() {
        return Holder.INSTANCE;
    }

    public List<Application> getByUserId(long userId) {
        List<Application> applications = null;

        try {
            ApplicationDAO applicationDAO = factory.createApplicationDAO();
            applications = applicationDAO.findAllByUserId(userId);
        } catch (Exception e) {
            LOGGER.error("Fail to get applications with userId = " + userId, e);
            throw new RuntimeException(e);
        }

        return applications;
    }

    @Override
    public List<Application> getAll() {
        List<Application> applications = null;

        try {
            ApplicationDAO applicationDAO = factory.createApplicationDAO();
            applications = applicationDAO.findAll();
        } catch (Exception e) {
            LOGGER.error("Fail to get all applications", e);
            throw new RuntimeException(e);
        }

        return applications;
    }

    @Override
    public Application getById(long id) {
        Application application = null;

        try {
            ApplicationDAO applicationDAO = factory.createApplicationDAO();
            application = applicationDAO.findById(id).get();
        } catch (Exception e) {
            LOGGER.error("Fail to find application with id = " + id, e);
            throw new RuntimeException(e);
        }

        return application;
    }

    @Override
    public void update(Application application) {
        try {
            ApplicationDAO applicationDAO = factory.createApplicationDAO();
            applicationDAO.update(application);
        } catch (Exception e) {
            LOGGER.error("Fail to update application with id = " + application.getId(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        try {
            ApplicationDAO applicationDAO = factory.createApplicationDAO();
            applicationDAO.delete(id);
        } catch (Exception e) {
            LOGGER.error("Fail to delete application with id: " + id, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(Application application) {
        try {
            ApplicationDAO applicationDAO = factory.createApplicationDAO();
            applicationDAO.insert(application);
        } catch (Exception e) {
            LOGGER.error("Fail to add application: " + application, e);
            throw new RuntimeException(e);
        }
    }

    public List<Application> getByPage(int page, int applicationsOnPage) {
        List<Application> applications = null;

        try {
            ApplicationDAO applicationDAO = factory.createApplicationDAO();
            applications = applicationDAO.findByPage(page, applicationsOnPage);
        } catch (Exception e) {
            LOGGER.error("Fail to get all applications by page, page = " + page +
                    ", applicationsOnPage = " + applicationsOnPage, e);
            throw new RuntimeException(e);
        }

        return applications;
    }

    public long getAllQuantity() {
        long applicationCounter;

        try {
            ApplicationDAO applicationDAO = factory.createApplicationDAO();
            applicationCounter = applicationDAO.countAll();
        } catch (Exception e) {
            LOGGER.error("Fail to get all applications quantity", e);
            throw new RuntimeException(e);
        }

        return applicationCounter;
    }

    public List<Application> getAcceptedByPage(int page, int applicationsOnPage) {
        List<Application> applications = null;

        try {
            ApplicationDAO applicationDAO = factory.createApplicationDAO();
            applications = applicationDAO.findAcceptedByPage(page, applicationsOnPage);
        } catch (Exception e) {
            LOGGER.error("Fail to get accepted applications by page, page = " + page +
                    ", applicationsOnPage = " + applicationsOnPage, e);
            throw new RuntimeException(e);
        }

        return applications;
    }

    public long getAcceptedQuantity() {
        long applicationCounter;

        try {
            ApplicationDAO applicationDAO = factory.createApplicationDAO();
            applicationCounter = applicationDAO.countAccepted();
        } catch (Exception e) {
            LOGGER.error("Fail to get accepted applications quantity", e);
            throw new RuntimeException(e);
        }

        return applicationCounter;
    }

    public List<Application> getByUserIdByPage(long userId, int page, int applicationsOnPage) {
        List<Application> applications = null;

        try {
            ApplicationDAO applicationDAO = factory.createApplicationDAO();
            applications = applicationDAO.findByUserIdByPage(userId, page, applicationsOnPage);
        } catch (Exception e) {
            LOGGER.error("Fail to get applications by user id: " + userId +
                    ", by page, page = " + page +
                    ", applicationsOnPage = " + applicationsOnPage, e);
            throw new RuntimeException(e);
        }

        return applications;
    }

    public long getAllByUserIdQuantity(long userId) {
//        long applicationCounter;
//
//        try {
//            ApplicationDAO applicationDAO = factory.createApplicationDAO();
//            applicationCounter = applicationDAO.countAllByUserId(userId);
//        } catch (Exception e) {
//            LOGGER.error("Fail to get applications quantity by user id", e);
//            throw new RuntimeException(e);
//        }
//
//        return applicationCounter;

            ApplicationDAO applicationDAO = factory.createApplicationDAO();
            return applicationDAO.countAllByUserId(userId);
    }
}
