package com.quar17esma.service;

import com.quar17esma.entity.Application;

import java.time.LocalDate;
import java.util.List;

public interface IApplicationService extends GenericService<Application> {
    List<Application> getByUserId(long id);
}
