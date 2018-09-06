package com.quar17esma.service;

import com.quar17esma.entity.User;

public interface IUserService extends GenericService<User> {
    User getByEmail(String email);

    User login(String email, String password);
}
