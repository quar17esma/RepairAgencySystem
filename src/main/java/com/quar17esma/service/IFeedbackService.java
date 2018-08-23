package com.quar17esma.service;

import com.quar17esma.entity.Feedback;

import java.util.List;

public interface IFeedbackService {
    List<Feedback> getAllFeedbacks();

    List<Feedback> getFeedbacksByPage(int page, int feedbacksOnPage);

    long getAllFeedbacksQuantity();

    Feedback getFeedbackById(long id);

    void deleteFeedbackById(long id);

    void addFeedback(Feedback feedback);

    void updateFeedback(Feedback feedback);
}
