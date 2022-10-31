package com.stahovskyi.onlineshop.web.servlet;

import com.stahovskyi.onlineshop.service.ProductService;
import com.stahovskyi.onlineshop.web.util.PageGenerator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.HashMap;

@AllArgsConstructor
public class DeleteServlet extends HttpServlet {

    private final PageGenerator pageGenerator = PageGenerator.instance();
    private ProductService productService;

   /* @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String page = pageGenerator.getPage("delete_product_response.html", new HashMap<>());
        response.getWriter().write(page);
    }*/

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        productService.delete(id);

        String page = pageGenerator.getPage("delete_product_response.html", new HashMap<>());
        response.getWriter().write(page);
    }
}
