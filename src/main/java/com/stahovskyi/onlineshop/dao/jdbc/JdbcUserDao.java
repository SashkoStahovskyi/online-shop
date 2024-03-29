package com.stahovskyi.onlineshop.dao.jdbc;

import com.stahovskyi.onlineshop.dao.UserDao;
import com.stahovskyi.onlineshop.dao.jdbc.mapper.UserRowMapper;
import com.stahovskyi.onlineshop.entity.Credentials;
import com.stahovskyi.onlineshop.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JdbcUserDao implements UserDao {
    private static final String GET_USER_QUERY = "SELECT user_name, hashed_password, salt, role FROM users WHERE (user_name) LIKE ?;";
    private static final String SAVE_USER_QUERY = "INSERT INTO users (user_name, hashed_password, salt, role) VALUES (?, ?, ?, ?)";
    private final static UserRowMapper USER_ROW_MAPPER = new UserRowMapper();
    private final DataSource dataSource;


    @Override
    public Optional<User> get(Credentials credentials) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_QUERY)) {
            preparedStatement.setString(1, credentials.getUserName());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet == null) {
                    log.info("No user with specified login in DB !");
                    return Optional.empty();
                }
                if (resultSet.next()) {
                    return Optional.of(USER_ROW_MAPPER.mapRow(resultSet));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable get user from DB !", e);
        }
        return Optional.empty();
    }

    @Override
    public void save(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER_QUERY)) {

            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getHashedPassword());
            preparedStatement.setString(3, user.getSalt());
            preparedStatement.setString(4, user.getRole().toString());
            preparedStatement.executeUpdate();
            log.info("Executed: {}", SAVE_USER_QUERY);

        } catch (SQLException e) {
            throw new RuntimeException(" Unable to save new user to DB !", e);
        }
    }

}
