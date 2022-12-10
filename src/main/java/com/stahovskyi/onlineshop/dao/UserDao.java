package com.stahovskyi.onlineshop.dao;

import com.stahovskyi.onlineshop.entity.User;

import java.util.Optional;

public interface UserDao {

    void save(User user);

    Optional<User> get(String login);
}
