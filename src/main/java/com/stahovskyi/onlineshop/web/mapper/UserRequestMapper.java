package com.stahovskyi.onlineshop.web.mapper;

import com.stahovskyi.onlineshop.entity.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Locale;

public class UserRequestMapper {

    public static User toUser(HttpServletRequest request) {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        return User.builder()
                .username(username)
                .password(password)
                .build();
    }
}
