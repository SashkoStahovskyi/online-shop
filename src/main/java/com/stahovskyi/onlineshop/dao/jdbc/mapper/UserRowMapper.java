package com.stahovskyi.onlineshop.dao.jdbc.mapper;

import com.stahovskyi.onlineshop.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper {

    public User mapRow(ResultSet resultSet) throws SQLException {

        return User.builder()
                .userName(resultSet.getString("user_name"))
                .hashedPassword(resultSet.getString("hashed_password"))
                .salt(resultSet.getString("salt"))
                .role(User.Role.valueOf(resultSet.getString("role")))
                .build();
    }
}
