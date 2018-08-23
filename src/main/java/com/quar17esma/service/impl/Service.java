package com.quar17esma.service.impl;

import com.quar17esma.dao.ConnectionPool;
import com.quar17esma.dao.DaoFactory;

public abstract class Service {
    protected DaoFactory factory;
    protected ConnectionPool connectionPool;

    public Service(DaoFactory factory, ConnectionPool connectionPool) {
        this.factory = factory;
        this.connectionPool = connectionPool;
    }
}
