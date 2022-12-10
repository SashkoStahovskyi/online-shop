package com.stahovskyi.onlineshop.web.servlet;

import com.stahovskyi.onlineshop.entity.Product;
import com.stahovskyi.onlineshop.service.ProductService;
import com.stahovskyi.onlineshop.util.PageGenerator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
public class ViewAllServlet extends HttpServlet {
    private final PageGenerator pageGenerator = PageGenerator.instance();
    private final ProductService productService;


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        showAllProducts(response);
    }
    private void showAllProducts(HttpServletResponse response) throws IOException {
        HashMap<String, Object> pageData = new HashMap<>();
        List<Product> allProducts = productService.getAll();
        pageData.put("products", allProducts);
        String page = pageGenerator.getPage("all_products.html", pageData);
        response.getWriter().write(page);
    }
}
