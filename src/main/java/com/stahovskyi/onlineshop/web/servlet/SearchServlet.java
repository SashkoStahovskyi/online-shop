package com.stahovskyi.onlineshop.web.servlet;

import com.stahovskyi.onlineshop.entity.Product;
import com.stahovskyi.onlineshop.entity.Session;
import com.stahovskyi.onlineshop.service.ProductService;
import com.stahovskyi.onlineshop.service.SecurityService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.stahovskyi.onlineshop.util.PageGenerator.getPageGeneratorInstance;
import static com.stahovskyi.onlineshop.web.util.RequestUtil.getRequestToken;

@AllArgsConstructor
public class SearchServlet extends HttpServlet {
    private final SecurityService securityService;
    private final ProductService productService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Session session = securityService.getSession(getRequestToken(request));
        String searchText = request.getParameter("search-text");
        List<Product> productsList = productService.search(searchText);

        Map<String, Object> pageData = new HashMap<>();
        pageData.put("products", productsList);
        pageData.put("userRole", session.getUser().getRole());

        response.getWriter()
                .write(getPageGeneratorInstance()
                        .getPage("all_products.html", pageData));
    }

}
