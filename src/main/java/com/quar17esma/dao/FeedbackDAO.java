package com.quar17esma.dao;

import com.quar17esma.entity.Feedback;

import java.util.List;

public interface FeedbackDAO extends GenericDAO<Feedback> {
    List<Feedback> findByPage(long page, int feedbackOnPage);

    long countAll();
}
