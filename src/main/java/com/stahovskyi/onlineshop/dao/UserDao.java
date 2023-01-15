package com.stahovskyi.onlineshop.dao;

import com.stahovskyi.onlineshop.entity.User;

import java.util.Optional;

public interface UserDao {

    Optional<User> get(String login);

    void save(String username, String hashedPassword, String salt);
}
