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

        ApplicationDAO applicationDAO = factory.createApplicationDAO();
        applications = applicationDAO.findAllByUserId(userId);

        return applications;
    }

    @Override
    public List<Application> getAll() {
        List<Application> applications = null;

        ApplicationDAO applicationDAO = factory.createApplicationDAO();
        applications = applicationDAO.findAll();

        return applications;
    }

    @Override
    public Application getById(long id) {
        Application application = null;

        ApplicationDAO applicationDAO = factory.createApplicationDAO();
        application = applicationDAO.findById(id).get();

        return application;
    }

    @Override
    public void update(Application application) {
        ApplicationDAO applicationDAO = factory.createApplicationDAO();
        applicationDAO.update(application);
    }

    @Override
    public void delete(long id) {
        ApplicationDAO applicationDAO = factory.createApplicationDAO();
        applicationDAO.delete(id);
    }

    @Override
    public void add(Application application) {
        ApplicationDAO applicationDAO = factory.createApplicationDAO();
        applicationDAO.insert(application);
    }

    public List<Application> getByPage(int page, int applicationsOnPage) {
        List<Application> applications = null;

        ApplicationDAO applicationDAO = factory.createApplicationDAO();
        applications = applicationDAO.findByPage(page, applicationsOnPage);

        return applications;
    }

    public long getAllQuantity() {
        long applicationCounter;

        ApplicationDAO applicationDAO = factory.createApplicationDAO();
        applicationCounter = applicationDAO.countAll();

        return applicationCounter;
    }

    public List<Application> getAcceptedByPage(int page, int applicationsOnPage) {
        List<Application> applications = null;

        ApplicationDAO applicationDAO = factory.createApplicationDAO();
        applications = applicationDAO.findAcceptedByPage(page, applicationsOnPage);

        return applications;
    }

    public long getAcceptedQuantity() {
        ApplicationDAO applicationDAO = factory.createApplicationDAO();
        long applicationCounter = applicationDAO.countAccepted();

        return applicationCounter;
    }

    public List<Application> getByUserIdByPage(long userId, int page, int applicationsOnPage) {
        List<Application> applications = null;

        ApplicationDAO applicationDAO = factory.createApplicationDAO();
        applications = applicationDAO.findByUserIdByPage(userId, page, applicationsOnPage);

        return applications;
    }

    public long getAllByUserIdQuantity(long userId) {
        ApplicationDAO applicationDAO = factory.createApplicationDAO();
        return applicationDAO.countAllByUserId(userId);
    }
}
