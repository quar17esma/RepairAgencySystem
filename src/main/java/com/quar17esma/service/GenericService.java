package com.quar17esma.service;

import com.quar17esma.entity.Entity;

import java.util.List;

public interface GenericService<T extends Entity> {
    List<T> getAll();
    T getById(long id);
    void update(T item);
    void delete(long id);
    void add(T item);
}
