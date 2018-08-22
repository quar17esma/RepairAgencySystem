package com.quar17esma.dao;

import com.quar17esma.entity.User;

import java.util.Optional;

public interface UserDAO extends GenericDAO<User> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
}
