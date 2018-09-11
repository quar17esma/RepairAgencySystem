package com.quar17esma.dao.impl;

import com.quar17esma.dao.ConnectionPool;
import com.quar17esma.dao.GenericDAO;
import com.quar17esma.entity.Entity;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class JDBCGenericDAO<T extends Entity> implements GenericDAO<T> {
    protected Logger logger;

    protected ConnectionPool connectionPool;

    protected String findAll;
    protected String findById;
    protected String delete;
    protected String update;
    protected String insert;

    @Override
    public List<T> findAll() {
        List<T> items = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(findAll)) {
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                T item = createItem(rs);
                items.add(item);
            }
        } catch (SQLException e) {
            logger.error("Fail to find items", e);
        }

        logger.info("Found all items");
        return items;
    }

    protected abstract T createItem(ResultSet rs) throws SQLException;

    @Override
    public Optional<T> findById(long id) {
        Optional<T> item = Optional.empty();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(findById)) {
            query.setLong(1, id);
            ResultSet rs = query.executeQuery();
            if (rs.next()) {
                T it = createItem(rs);
                item = Optional.of(it);
            }
        } catch (SQLException e) {
            logger.error("Fail to find item with id = " + id, e);
        }

        logger.info("Found item by id, item: " + item + ", id: " + id);
        return item;
    }

    @Override
    public boolean update(T item) {
        boolean result = false;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(update)) {
            setUpdateQueryParams(query, item);
            query.executeUpdate();
            result = true;
        } catch (SQLException e) {
            logger.error("Fail to update item, item: " + item, e);
        }

        logger.info("Updated item, item: " + item);
        return result;
    }

    protected abstract void setUpdateQueryParams(PreparedStatement query, T item) throws SQLException;

    @Override
    public boolean delete(long id) {
        boolean result = false;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(delete)) {
            query.setLong(1, id);
            query.executeUpdate();
            result = true;
        } catch (SQLException e) {
            logger.error("Fail to delete item with id = " + id, e);
        }

        logger.info("Deleted item by id, id: " + id);
        return result;
    }

    @Override
    public long insert(T item) {
        long result = -1;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            setInsertQueryParams(query, item);
            query.executeUpdate();
            ResultSet rsId = query.getGeneratedKeys();
            if (rsId.next()) {
                result = rsId.getLong(1);
                item.setId(result);
            }
        } catch (SQLException e) {
            logger.error("Fail to insert item: " + item, e);
        }

        logger.info("Inserted item to DB, item: " + item);
        return result;
    }

    protected abstract void setInsertQueryParams(PreparedStatement query, T item) throws SQLException;
}
