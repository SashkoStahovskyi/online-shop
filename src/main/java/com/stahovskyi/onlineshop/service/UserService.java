
package com.stahovskyi.onlineshop.service;

import com.stahovskyi.onlineshop.dao.UserDao;
import com.stahovskyi.onlineshop.entity.User;
import com.stahovskyi.onlineshop.web.security.entity.Credentials;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public Optional<User> getUser(Credentials credentials) {
        return userDao.get(credentials);
    }

    public void save(User user) {
        userDao.save(user);
    }

}



