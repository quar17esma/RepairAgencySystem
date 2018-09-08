package com.quar17esma.dao;

import com.quar17esma.entity.Application;

import java.util.List;

public interface ApplicationDAO extends GenericDAO<Application> {
    List<Application> findAllByUserId(long userId);

    List<Application> findAllByPage(long page, int applicationsOnPage);

    long countAll();

    List<Application> findAcceptedByPage(long page, int applicationsOnPage);

    long countAccepted();

    List<Application> findByUserIdByPage(long userId, long page, int applicationsOnPage);

    long countAllByUserId(long userId);
}
