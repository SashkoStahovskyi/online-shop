package com.stahovskyi.onlineshop.web.servlet;

import com.stahovskyi.onlineshop.entity.Product;
import com.stahovskyi.onlineshop.entity.Session;
import com.stahovskyi.onlineshop.service.ProductService;
import com.stahovskyi.onlineshop.service.SecurityService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.HashMap;

import static com.stahovskyi.onlineshop.util.PageGenerator.getPageGeneratorInstance;
import static com.stahovskyi.onlineshop.web.mapper.ProductRequestMapper.toProduct;
import static com.stahovskyi.onlineshop.web.util.RequestUtil.getProductId;
import static com.stahovskyi.onlineshop.web.util.RequestUtil.getRequestToken;

@RequiredArgsConstructor
public class EditeServlet extends HttpServlet {
    private final SecurityService securityService;
    private final ProductService productService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Session session = securityService.getSession(getRequestToken(request));
        Product product = productService.getById(getProductId(request)).orElseThrow();

        HashMap<String, Object> pageData = new HashMap<>();
        pageData.put("product", product);
        pageData.put("userRole", session.getUser().getRole());

        response.getWriter()
                .write(getPageGeneratorInstance()
                        .getPage("edit_products.html", pageData));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Product product = toProduct(request);
        productService.edit(product);
        response.sendRedirect("/products");
    }

}
