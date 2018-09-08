package com.quar17esma.service.impl;

import com.quar17esma.dao.ApplicationDAO;
import com.quar17esma.dao.DaoFactory;
import com.quar17esma.entity.Application;
import com.quar17esma.service.IApplicationService;
import org.apache.log4j.Logger;

import java.util.List;

public class ApplicationService extends Service<Application> implements IApplicationService {
    private ApplicationDAO applicationDAO;

    private ApplicationService(DaoFactory factory) {
        super(factory);
        this.applicationDAO = factory.createApplicationDAO();
        dao = this.applicationDAO;
        logger = Logger.getLogger(ApplicationService.class);
    }

    private static class Holder {
        private static ApplicationService INSTANCE = new ApplicationService(DaoFactory.getInstance());
    }

    public static ApplicationService getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    protected void setId(Application item, long id) {
        item.setId(id);
    }

    public List<Application> getByUserId(long userId) {
        List<Application> applications = applicationDAO.findAllByUserId(userId);

        logger.info("Got applications by user id, applications: " + applications +
                "user id: " + userId);
        return applications;
    }

    public List<Application> getByPage(long page, int applicationsOnPage) {
        List<Application> applications = applicationDAO.findAllByPage(page, applicationsOnPage);

        logger.info("Got applications by page, applications: " + applications +
                ", page: " + page +
                ", applicationsOnPage: " + applicationsOnPage);
        return applications;
    }

    public long getAllQuantity() {
        return applicationDAO.countAll();
    }

    public List<Application> getAcceptedByPage(long page, int applicationsOnPage) {
        List<Application> applications = applicationDAO.findAcceptedByPage(page, applicationsOnPage);

        logger.info("Got accepted applications by page, applications: " + applications +
                ", page: " + page +
                ", applicationsOnPage: " + applicationsOnPage);
        return applications;
    }

    public long getAcceptedQuantity() {
        return applicationDAO.countAccepted();
    }

    public List<Application> getByUserIdByPage(long userId, long page, int applicationsOnPage) {
        List<Application> applications = applicationDAO.findByUserIdByPage(userId, page, applicationsOnPage);

        logger.info("Got applications by user id, by page, applications: " + applications +
                ", user id: " + userId +
                ", page: " + page +
                ", applicationsOnPage: " + applicationsOnPage);
        return applications;
    }

    public long getAllByUserIdQuantity(long userId) {
        return applicationDAO.countAllByUserId(userId);
    }
}
