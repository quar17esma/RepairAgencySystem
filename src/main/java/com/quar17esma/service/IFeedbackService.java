package com.quar17esma.service;

import com.quar17esma.entity.Feedback;

import java.util.List;

public interface IFeedbackService {
    List<Feedback> getAll();

    List<Feedback> getByPage(int page, int feedbacksOnPage);

    long getAllQuantity();

    Feedback getById(long id);

    void deleteById(long id);

    void add(Feedback feedback);

    void update(Feedback feedback);
}
