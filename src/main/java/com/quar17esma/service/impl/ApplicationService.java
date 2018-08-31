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
        return applicationDAO.findAllByUserId(userId);
    }

    @Override
    public List<Application> getAll() {
        return applicationDAO.findAll();
    }

    @Override
    public Application getById(long id) {
        return applicationDAO.findById(id).get();
    }

    @Override
    public void update(Application application) {
        applicationDAO.update(application);
    }

    @Override
    public void delete(long id) {
        applicationDAO.delete(id);
    }

    @Override
    public void add(Application application) {
        applicationDAO.insert(application);
    }

    public List<Application> getByPage(int page, int applicationsOnPage) {
        return applicationDAO.findByPage(page, applicationsOnPage);
    }

    public long getAllQuantity() {
        return applicationDAO.countAll();
    }

    public List<Application> getAcceptedByPage(int page, int applicationsOnPage) {
        return applicationDAO.findAcceptedByPage(page, applicationsOnPage);
    }

    public long getAcceptedQuantity() {
        return applicationDAO.countAccepted();
    }

    public List<Application> getByUserIdByPage(long userId, int page, int applicationsOnPage) {
        return applicationDAO.findByUserIdByPage(userId, page, applicationsOnPage);
    }

    public long getAllByUserIdQuantity(long userId) {
        return applicationDAO.countAllByUserId(userId);
    }
}
