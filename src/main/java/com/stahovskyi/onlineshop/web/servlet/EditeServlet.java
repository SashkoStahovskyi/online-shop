package com.stahovskyi.onlineshop.web.servlet;

import com.stahovskyi.onlineshop.entity.Product;
import com.stahovskyi.onlineshop.service.ProductService;
import com.stahovskyi.onlineshop.util.PageGenerator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.HashMap;

import static com.stahovskyi.onlineshop.web.mapper.ProductRequestMapper.toProduct;
import static com.stahovskyi.onlineshop.web.util.RequestUtil.getProductId;

@RequiredArgsConstructor
public class EditeServlet extends HttpServlet {
    private final PageGenerator pageGenerator = PageGenerator.instance();
    private final ProductService productService;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Product product = productService.getById(getProductId(request))
                .orElseThrow();

        HashMap<String, Object> pageMap = new HashMap<>();
        pageMap.put("product", product);

        response.getWriter()
                .write(pageGenerator.getPage("edit_products.html", pageMap));
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Product product = toProduct(request);
        productService.edit(product);

        response.getWriter()
                .write(pageGenerator.getPage("edit_products_response.html", new HashMap<>()));
    }

}
