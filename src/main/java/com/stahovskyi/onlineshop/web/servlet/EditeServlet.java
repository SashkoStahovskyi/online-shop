package com.stahovskyi.onlineshop.web.servlet;

import com.stahovskyi.onlineshop.entity.Product;
import com.stahovskyi.onlineshop.service.ProductService;
import com.stahovskyi.onlineshop.web.mapper.ProductRequestMapper;
import com.stahovskyi.onlineshop.web.util.PageGenerator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;

public class EditeServlet extends HttpServlet {
    private final ProductService productService;
    private final PageGenerator pageGenerator = PageGenerator.instance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HashMap<String, Object> pageMap = new HashMap();
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productService.getById(id).orElseThrow();
        pageMap.put("product", product);
        String page = this.pageGenerator.getPage("edit_products.html", pageMap);
        response.getWriter().write(page);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Product product = ProductRequestMapper.toProduct(request);
        this.productService.edit(product);
        String page = this.pageGenerator.getPage("edit_products_response.html", new HashMap());
        response.getWriter().write(page);
    }

    public EditeServlet(ProductService productService) {
        this.productService = productService;
    }
}
