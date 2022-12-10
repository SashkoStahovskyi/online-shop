
package com.stahovskyi.onlineshop.service;

import com.stahovskyi.onlineshop.dao.UserDao;
import com.stahovskyi.onlineshop.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public Optional<User> getUser(String login) {
        return userDao.get(login);
    }


   /* public void save(User user, String encryptedPassword) {
        userDao.save(user, encryptedPassword);
    }*/
}



