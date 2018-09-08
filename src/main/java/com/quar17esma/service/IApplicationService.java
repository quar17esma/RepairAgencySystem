package com.quar17esma.service;

import com.quar17esma.entity.Application;

import java.util.List;

public interface IApplicationService extends GenericService<Application> {
    List<Application> getByUserId(long id);

    List<Application> getByPage(long page, int applicationsOnPage);

    long getAllQuantity();

    List<Application> getAcceptedByPage(long page, int applicationsOnPage);

    long getAcceptedQuantity();

    List<Application> getByUserIdByPage(long userId, long page, int applicationsOnPage);

    long getAllByUserIdQuantity(long userId);
}
