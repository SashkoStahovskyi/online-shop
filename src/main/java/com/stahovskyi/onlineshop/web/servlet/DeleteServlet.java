package com.stahovskyi.onlineshop.web.servlet;

import com.stahovskyi.onlineshop.service.ProductService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;

import static com.stahovskyi.onlineshop.web.util.RequestUtil.getProductId;

@AllArgsConstructor
public class DeleteServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        productService.delete(getProductId(request));
        response.sendRedirect("/products");
    }
}
