package com.stahovskyi.onlineshop.dao.jdbc;

import com.stahovskyi.onlineshop.dao.ProductDao;
import com.stahovskyi.onlineshop.dao.jdbc.mapper.ProductRowMapper;
import com.stahovskyi.onlineshop.entity.Product;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcProductDao implements ProductDao {
    private static final String DELETE_PRODUCT_QUERY = "DELETE FROM products WHERE id=?";
    private static final String UPDATE_PRODUCT_QUERY = "UPDATE products SET name=?, price=?, description=? WHERE id=?";
    private static final String GET_ALL_PRODUCT_QUERY = "SELECT id, name, price, description, date FROM products ORDER BY id";
    private static final String ADD_PRODUCT_QUERY = "INSERT INTO products (name, price, description, date) VALUES (?, ?, ?, ?)";
    private static final String GET_PRODUCT_BY_ID_QUERY = "SELECT id, name, price, description, date FROM products WHERE id=?";
    private static final String SEARCH_PRODUCT_QUERY = "SELECT id,name, price, date, description FROM products WHERE name LIKE CONCAT( '%',?,'%') OR description LIKE CONCAT( '%',?,'%')";
    private static final ProductRowMapper PRODUCT_ROW_MAPPER = new ProductRowMapper();
   // private final DataSource dataSource;
    private final ConnectionFactory dataSource;

    @Override
    public List<Product> findAll() {  // todo -> integration test needed
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_PRODUCT_QUERY)) {

            List<Product> allProductsList = new ArrayList<>();

            while (resultSet.next()) {
                Product product = PRODUCT_ROW_MAPPER.mapRow(resultSet);
                allProductsList.add(product);
            }
            // log.info("Executed: {}", GET_ALL_PRODUCT_QUERY); // todo -> investigation
            return allProductsList;

        } catch (Exception e) {
            throw new RuntimeException("Unable to get all products list from DB", e);
        }
    }

    @Override
    public void add(Product product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_PRODUCT_QUERY)) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(product.getDate()));
            preparedStatement.executeUpdate();
            // todo log.info("Executed: {}", preparedStatement);

        } catch (SQLException e) {
            throw new RuntimeException(" Unable add product to DB ! ", e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_QUERY)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            // todo log.info("Executed: {}", preparedStatement);

        } catch (SQLException e) {
            throw new RuntimeException(" Unable to delete from DB !", e);
        }
    }

    @Override
    public void update(Product product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_QUERY)) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setInt(4, product.getId());

            preparedStatement.executeUpdate();
            // todo log.info("Executed: {}", preparedStatement);

        } catch (Exception e) {
            throw new RuntimeException(" Unable to update !", e);
        }
    }

    public Optional<Product> getById(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_BY_ID_QUERY)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // todo: log.info("Executed: {}", preparedStatement);
                if (resultSet.next()) {
                    return Optional.of(PRODUCT_ROW_MAPPER.mapRow(resultSet));
                }
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to get product from DB", e);
        }
    }

    @Override
    public List<Product> search(String searchText) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_PRODUCT_QUERY)) {

            preparedStatement.setString(1, searchText);
            preparedStatement.setString(2, searchText);
            List<Product> productsList = new ArrayList<>();

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = PRODUCT_ROW_MAPPER.mapRow(resultSet);
                    productsList.add(product);
                }
            }
            return productsList;
            // todo : need Logs here
        } catch (SQLException e) {
            throw new RuntimeException("No matches found in DB !", e);
        }
    }
}
