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
        List<Product> allProducts = productService.getAll();

        HashMap<String, Object> pageData = new HashMap<>();
        pageData.put("products", allProducts);

        response.getWriter()
                .write(pageGenerator.getPage("all_products.html", pageData));
    }

}
