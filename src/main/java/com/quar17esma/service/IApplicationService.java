package com.quar17esma.service;

import com.quar17esma.entity.Application;

import java.util.List;

public interface IApplicationService extends GenericService<Application> {
    List<Application> getByUserId(long id);

    List<Application> getByPage(int page, int applicationsOnPage);

    long getAllQuantity();
}
