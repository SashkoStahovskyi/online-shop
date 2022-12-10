package com.stahovskyi.onlineshop.web.servlet;

import com.stahovskyi.onlineshop.entity.Product;
import com.stahovskyi.onlineshop.service.ProductService;
import com.stahovskyi.onlineshop.util.PageGenerator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class SearchServlet extends HttpServlet {
    private final PageGenerator pageGenerator = PageGenerator.instance();
    private ProductService productService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        showAllProducts(request, response);
    }

    private void showAllProducts(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String searchText = request.getParameter("search-text");
        List<Product> productsList = productService.search(searchText);
        Map<String, Object> pageData = new HashMap<>();
        pageData.put("products", productsList);
        String page = pageGenerator.getPage("all_products.html", pageData);
        response.getWriter().write(page);
    }
}
