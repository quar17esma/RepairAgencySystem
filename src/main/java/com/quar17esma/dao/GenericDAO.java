package com.quar17esma.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDAO<T> extends AutoCloseable{
    List<T> findAll();
    Optional<T> findById(long id);
    boolean update(T item);
    boolean delete(long id);
    long insert(T item);
}
