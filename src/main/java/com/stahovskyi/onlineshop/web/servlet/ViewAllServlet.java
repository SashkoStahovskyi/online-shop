package com.stahovskyi.onlineshop.web.servlet;

import com.stahovskyi.onlineshop.entity.Product;
import com.stahovskyi.onlineshop.entity.Session;
import com.stahovskyi.onlineshop.service.ProductService;
import com.stahovskyi.onlineshop.service.SecurityService;
import com.stahovskyi.onlineshop.util.PageGenerator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static com.stahovskyi.onlineshop.web.util.RequestUtil.getRequestToken;

@AllArgsConstructor
public class ViewAllServlet extends HttpServlet {
    private final PageGenerator pageGenerator = PageGenerator.getPageGeneratorInstance();
    private final SecurityService securityService;
    private final ProductService productService;


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Product> allProducts = productService.getAll();
        Session session = securityService.getSession(getRequestToken(request));

        HashMap<String, Object> pageData = new HashMap<>();
        pageData.put("products", allProducts);
        pageData.put("userRole", session.getUser().getRole());

        response.getWriter()
                .write(pageGenerator.getPage("all_products.html", pageData));
    }

}
