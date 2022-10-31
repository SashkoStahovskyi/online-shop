package com.stahovskyi.onlineshop.dao.jdbc.mapper;

import com.stahovskyi.onlineshop.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ProductRowMapper {
    public Product mapRow(ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt("id");
        LocalDateTime date = resultSet.getTimestamp("date").toLocalDateTime(); // fixme : LocalDateTime  change for LocalDate
        String description = resultSet.getString("description");
        String name = resultSet.getString("name");
        double price = resultSet.getDouble("price");

        return Product.builder()
                .id(id)
                .date(date)
                .name(name)
                .price(price)
                .description(description)
                .build();
    }
}