package com.stahovskyi.onlineshop.dao.jdbc;

import com.stahovskyi.onlineshop.dao.UserDao;
import com.stahovskyi.onlineshop.entity.User;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcUserDao implements UserDao {
    //private final DataSource dataSource;
    private final ConnectionFactory dataSource;
    private static final String SAVE_USER_QUERY = "INSERT INTO users (user_name, password) VALUES (?, ?)";

    //private static final String GET_USER = ""

    @Override
    public void save(User user) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER_QUERY)) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();

            // todo -> LOGS here need

        } catch (SQLException e) {
            throw new RuntimeException("Unable to save user to DB !", e);
        }
    }

    @Override
    public boolean getUser(User user) {
        return false;
    }
}
