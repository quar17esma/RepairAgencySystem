package com.quar17esma.service;

import com.quar17esma.entity.User;

public interface ILoginService {
    User login(String email, String password);
}
