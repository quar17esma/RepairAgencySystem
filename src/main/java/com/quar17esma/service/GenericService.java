package com.quar17esma.service;

import com.quar17esma.entity.Entity;

import java.util.List;
import java.util.NoSuchElementException;

public interface GenericService<T extends Entity> {
    List<T> getAll();
    T getById(long id) throws NoSuchElementException;
    boolean update(T item);
    boolean delete(long id);
    boolean add(T item);
}
