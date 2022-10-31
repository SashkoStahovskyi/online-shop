package com.stahovskyi.onlineshop.dao.jdbc.mapper;

import com.stahovskyi.onlineshop.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.when;

class ProductRowMapperTest {

    @Test
    public void testMapRow() throws SQLException {
        //prepare
        ProductRowMapper productRowMapper = new ProductRowMapper();
        ResultSet mockedResultSet = Mockito.mock(ResultSet.class);
        when(mockedResultSet.getInt("id")).thenReturn(1);
        when(mockedResultSet.getString("name")).thenReturn("Bowl");
        when(mockedResultSet.getDouble("price")).thenReturn(33.20);

        //when
        Product product = productRowMapper.mapRow(mockedResultSet);

        // then
        Assertions.assertEquals(1, product.getId());
        Assertions.assertEquals("Bowl", product.getName());
        Assertions.assertEquals(33.20, product.getPrice());
    }
}