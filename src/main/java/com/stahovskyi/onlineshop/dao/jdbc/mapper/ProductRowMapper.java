package com.stahovskyi.onlineshop.dao.jdbc.mapper;

import com.stahovskyi.onlineshop.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper {

    public Product mapRow(ResultSet resultSet) throws SQLException {

      /*  LocalDateTime dateTime = resultSet.getTimestamp("date").toLocalDateTime();
        DateTimeFormatter date = DateTimeFormatter.ofPattern("dd/MM/yyyy");
*/
        return Product.builder()
                .id(resultSet.getInt("id"))
                .date(resultSet.getDate("date").toLocalDate())
                .name(resultSet.getString("name"))
                .price(resultSet.getDouble("price"))
                .description(resultSet.getString("description"))
                .build();
    }
}