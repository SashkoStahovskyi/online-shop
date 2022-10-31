package com.stahovskyi.onlineshop.dao.jdbc.mapper;

import com.stahovskyi.onlineshop.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper {

    User mapRow(ResultSet resultSet) throws SQLException {

        String username = resultSet.getString("username");
        String password = resultSet.getString("password");

        return User.builder()
                .username(username)
                .password(password)
                .build();
    }
}
