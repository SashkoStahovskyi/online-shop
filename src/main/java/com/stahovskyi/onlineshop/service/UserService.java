
package com.stahovskyi.onlineshop.service;

import com.stahovskyi.onlineshop.dao.UserDao;
import com.stahovskyi.onlineshop.entity.User;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public Optional<User> getUser(String login) {
        return userDao.get(login);
    }

    public void save(String username, String hashedPassword, String salt) {
        userDao.save(username, hashedPassword, salt);
    }

}



