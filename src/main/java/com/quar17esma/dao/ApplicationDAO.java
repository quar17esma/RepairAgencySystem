package com.quar17esma.dao;

import com.quar17esma.entity.Application;

import java.util.List;

public interface ApplicationDAO extends GenericDAO<Application> {
    List<Application> findAllByUserId(long userId);
}
