package com.quar17esma.service.impl;

import com.quar17esma.dao.ApplicationDAO;
import com.quar17esma.dao.DaoFactory;
import com.quar17esma.entity.Application;
import com.quar17esma.service.IApplicationService;
import org.apache.log4j.Logger;

import java.util.List;

public class ApplicationService extends Service implements IApplicationService {
    private static final Logger LOGGER = Logger.getLogger(ApplicationService.class);

    private ApplicationDAO applicationDAO;

    private ApplicationService(DaoFactory factory) {
        super(factory);
        this.applicationDAO = factory.createApplicationDAO();
    }

    private static class Holder {
        private static ApplicationService INSTANCE = new ApplicationService(DaoFactory.getInstance());
    }

    public static ApplicationService getInstance() {
        return Holder.INSTANCE;
    }

    public List<Application> getByUserId(long userId) {
        List<Application> applications = applicationDAO.findAllByUserId(userId);

        LOGGER.info("Got applications by user id, applications: " + applications +
                "user id: " + userId);
        return applications;
    }

    @Override
    public List<Application> getAll() {
        List<Application> applications = applicationDAO.findAll();

        LOGGER.info("Got all applications");
        return applications;
    }

    @Override
    public Application getById(long id) {
        Application application = applicationDAO.findById(id).get();

        LOGGER.info("Got application by id, id: " + id);
        return application;
    }

    @Override
    public void update(Application application) {
        applicationDAO.update(application);

        LOGGER.info("Updated application, application: " + application);
    }

    @Override
    public void delete(long id) {
        applicationDAO.delete(id);

        LOGGER.info("Deleted application by id, id: " + id);
    }

    @Override
    public void add(Application application) {
        applicationDAO.insert(application);

        LOGGER.info("Added application, application: " + application);
    }

    public List<Application> getByPage(long page, int applicationsOnPage) {
        List<Application> applications = applicationDAO.findAllByPage(page, applicationsOnPage);

        LOGGER.info("Got applications by page, applications: " + applications +
                ", page: " + page +
                ", applicationsOnPage: " + applicationsOnPage);
        return applications;
    }

    public long getAllQuantity() {
        return applicationDAO.countAll();
    }

    public List<Application> getAcceptedByPage(long page, int applicationsOnPage) {
        List<Application> applications = applicationDAO.findAcceptedByPage(page, applicationsOnPage);

        LOGGER.info("Got accepted applications by page, applications: " + applications +
                ", page: " + page +
                ", applicationsOnPage: " + applicationsOnPage);
        return applications;
    }

    public long getAcceptedQuantity() {
        return applicationDAO.countAccepted();
    }

    public List<Application> getByUserIdByPage(long userId, long page, int applicationsOnPage) {
        List<Application> applications = applicationDAO.findByUserIdByPage(userId, page, applicationsOnPage);

        LOGGER.info("Got applications by user id, by page, applications: " + applications +
                ", user id: " + userId +
                ", page: " + page +
                ", applicationsOnPage: " + applicationsOnPage);
        return applications;
    }

    public long getAllByUserIdQuantity(long userId) {
        return applicationDAO.countAllByUserId(userId);
    }
}
