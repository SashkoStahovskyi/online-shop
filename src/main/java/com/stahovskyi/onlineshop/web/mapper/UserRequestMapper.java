package com.stahovskyi.onlineshop.web.mapper;

import com.stahovskyi.onlineshop.entity.User;
import jakarta.servlet.http.HttpServletRequest;

public class UserRequestMapper {

    public static User toUser(HttpServletRequest request) {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        return User.builder()
                .userName(username)
                .hashedPassword(password)
                .build();
    }
}
