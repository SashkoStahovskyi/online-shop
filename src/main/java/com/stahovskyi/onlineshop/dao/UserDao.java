package com.stahovskyi.onlineshop.dao;

import com.stahovskyi.onlineshop.entity.Credentials;
import com.stahovskyi.onlineshop.entity.User;

import java.util.Optional;

public interface UserDao {

    Optional<User> get(Credentials credentials);

    void save(Credentials credentials, String hashedPassword, String salt);
}
