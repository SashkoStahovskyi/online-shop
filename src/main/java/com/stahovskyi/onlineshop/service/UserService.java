
package com.stahovskyi.onlineshop.service;

import com.stahovskyi.onlineshop.dao.UserDao;
import com.stahovskyi.onlineshop.entity.User;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public void save(User user) {
        userDao.save(user);
    }

    public boolean getById(User user) {
        return userDao.getUser(user);
    }
}


