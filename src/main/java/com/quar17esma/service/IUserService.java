package com.quar17esma.service;

import com.quar17esma.entity.User;

public interface IUserService {
    void registerUser(User user);

    User getUserByEmail(String email);
}
