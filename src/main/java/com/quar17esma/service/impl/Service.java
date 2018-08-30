package com.quar17esma.service.impl;

import com.quar17esma.dao.DaoFactory;

public abstract class Service {
    protected DaoFactory factory;

    public Service(DaoFactory factory) {
        this.factory = factory;
    }
}
