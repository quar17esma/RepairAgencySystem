package com.quar17esma.entity;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    protected long id;

    public Entity() {
    }

    public Entity(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
