package com.stahovskyi.onlineshop.web.mapper;

import com.stahovskyi.onlineshop.entity.Product;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;

public class ProductRequestMapper {

    public static Product toProduct(HttpServletRequest request) {

        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        String description = request.getParameter("description");

        Product product = Product.builder()
                .name(name)
                .price(price)
                .description(description)
                .date(LocalDateTime.now())
                .build();

        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            product.setId(id);
        }
        return product;
    }
}

