package com.quar17esma.service;

import java.util.List;
import java.util.Optional;

public interface GenericService<T> {
    List<T> getAll();
    T getById(long id);
    void update(T item);
    void delete(long id);
    void add(T item);
}
