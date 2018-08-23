package com.quar17esma.service;

import com.quar17esma.entity.Feedback;

import java.util.List;

public interface IFeedbackService extends GenericService<Feedback> {
    List<Feedback> getByPage(int page, int feedbacksOnPage);

    long getAllQuantity();
}
