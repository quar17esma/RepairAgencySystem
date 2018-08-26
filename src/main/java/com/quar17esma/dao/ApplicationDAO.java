package com.quar17esma.dao;

import com.quar17esma.entity.Application;
import com.quar17esma.entity.Feedback;

import java.util.List;

public interface ApplicationDAO extends GenericDAO<Application> {
    List<Application> findAllByUserId(long userId);

    List<Application> findByPage(int page, int applicationsOnPage);

    long countAll();
}
