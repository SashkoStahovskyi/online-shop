package com.stahovskyi.onlineshop.web.servlet;

import com.stahovskyi.onlineshop.entity.Product;
import com.stahovskyi.onlineshop.service.ProductService;
import com.stahovskyi.onlineshop.web.util.PageGenerator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.HashMap;

import static com.stahovskyi.onlineshop.web.mapper.ProductRequestMapper.toProduct;

@AllArgsConstructor
public class AddServlet extends HttpServlet {
    private ProductService productService;
    private final PageGenerator pageGenerator = PageGenerator.instance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        String page = pageGenerator.getPage("add_product.html", new HashMap<>());
        response.getWriter().println(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Product product = toProduct(request);
        productService.addProduct(product);

        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("add_product_response.html",new HashMap<>());
        response.getWriter().println(page);
    }
}
