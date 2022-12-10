package com.stahovskyi.onlineshop.dao.jdbc;

import com.stahovskyi.onlineshop.dao.UserDao;
import com.stahovskyi.onlineshop.dao.jdbc.mapper.UserRowMapper;
import com.stahovskyi.onlineshop.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JdbcUserDao implements UserDao {
    //private final DataSource dataSource;
    private final ConnectionFactory dataSource;

    private static final String SAVE_USER_QUERY = "INSERT INTO users (user_name, password) VALUES (?, ?)";

    private static final String GET_USER_QUERY = "SELECT user_name, password FROM users WHERE (user_name) LIKE ?;";

    private final static UserRowMapper USER_ROW_MAPPER = new UserRowMapper();

    @Override
    public void save(User user) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER_QUERY)) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
            log.info("Executed: {}", SAVE_USER_QUERY);

        } catch (SQLException e) {
            throw new RuntimeException("Unable to save user to DB !", e);
        }
    }

    @Override
    public Optional<User> get(String login) {
        // String searchWord = "%" + login + "%";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_QUERY)) {
            preparedStatement.setString(1, login); // todo --> password

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

}
