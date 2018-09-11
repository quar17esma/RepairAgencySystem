package com.quar17esma.service;

import com.quar17esma.entity.Application;

import java.util.List;
import java.util.NoSuchElementException;

public interface IApplicationService extends GenericService<Application> {
    List<Application> getByUserId(long id);

    List<Application> getByPage(long page, int applicationsOnPage);

    long getAllQuantity();

    List<Application> getAcceptedByPage(long page, int applicationsOnPage);

    long getAcceptedQuantity();

    List<Application> getByUserIdByPage(long userId, long page, int applicationsOnPage);

    long getAllByUserIdQuantity(long userId);

    boolean acceptApplication(long applicationId, int price) throws NoSuchElementException;

    boolean declineApplication(long applicationId, String declineReason) throws NoSuchElementException;

    boolean completeApplication(long applicationId) throws NoSuchElementException;
}
