package com.quar17esma.service.impl;

import com.quar17esma.dao.DaoFactory;
import com.quar17esma.dao.GenericDAO;
import com.quar17esma.exceptions.BusyEmailException;
import com.quar17esma.service.GenericService;
import org.apache.log4j.Logger;

import java.util.List;

public abstract class Service<T> implements GenericService<T> {
    protected Logger logger;
    protected DaoFactory factory;
    protected GenericDAO<T> dao;

    public Service(DaoFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<T> getAll() {
        List<T> items = dao.findAll();

        logger.info("Got all items");
        return items;
    }

    @Override
    public T getById(long id) {
        T item = dao.findById(id).get();

        logger.info("Got item by id, id: " + id + ", item: " + item);
        return item;
    }

    @Override
    public void update(T item) {
        dao.update(item);

        logger.info("Updated item, item: " + item);
    }

    @Override
    public void delete(long id) {
        dao.delete(id);

        logger.info("Deleted item of class: by id, id: " + id);
    }

    @Override
    public void add(T item) throws BusyEmailException {
        long id = dao.insert(item);
        setId(item, id);

        logger.info("Added item, item: " + item);
    }

    protected abstract void setId(T item, long id);
}
