package com.quar17esma.service.impl;

import com.quar17esma.dao.DaoFactory;
import com.quar17esma.dao.GenericDAO;
import com.quar17esma.entity.Entity;
import com.quar17esma.exceptions.BusyEmailException;
import com.quar17esma.service.GenericService;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public abstract class Service<T extends Entity> implements GenericService<T> {
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
    public T getById(long id) throws NoSuchElementException {
        T item;

        Optional<T> optional = dao.findById(id);
        try {
            item = optional.get();
            logger.info("Got item by id, id: " + id + ", item: " + item);
        } catch (NoSuchElementException e) {
            logger.error("Fail to getById item, id: " + id, e);
            throw e;
        }

        return item;
    }

    @Override
    public boolean update(T item) {
        boolean result = dao.update(item);
        if (result) {
            logger.info("Updated item, item: " + item);
        } else {
            logger.error("Fail to update item, item: " + item);
        }

        return result;
    }

    @Override
    public boolean delete(long id) {
        boolean result = dao.delete(id);
        if (result) {
            logger.info("Deleted item by id, id: " + id);
        } else {
            logger.error("Fail to delete item by id, id: " + id);
        }

        return result;
    }

    @Override
    public boolean add(T item) throws BusyEmailException {
        boolean result = false;
        long id = dao.insert(item);
        if (id > 0) {
            item.setId(id);
            result = true;
            logger.info("Added item, item: " + item);
        } else {
            logger.error("Fail to add item, item: " + item);
        }

         return result;
    }
}
