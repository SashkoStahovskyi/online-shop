package com.stahovskyi.onlineshop.dao;

import com.stahovskyi.onlineshop.entity.User;

public interface UserDao {

    void save(User user);

    boolean getUser(User user);
}
