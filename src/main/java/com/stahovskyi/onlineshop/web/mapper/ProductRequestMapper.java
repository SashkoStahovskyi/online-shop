package com.stahovskyi.onlineshop.web.mapper;

import com.stahovskyi.onlineshop.entity.Product;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;

public class ProductRequestMapper {

    public static Product toProduct(HttpServletRequest request) {

        Product product = Product.builder()
                .name(request.getParameter("name"))
                .price(Double.parseDouble(request.getParameter("price")))
                .description(request.getParameter("description"))
                .date(LocalDate.now())
                .build();

        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            product.setId(id);
        }
        return product;
    }
}

