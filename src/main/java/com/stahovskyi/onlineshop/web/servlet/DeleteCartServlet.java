package com.stahovskyi.onlineshop.web.servlet;

import com.stahovskyi.onlineshop.service.SecurityService;
import com.stahovskyi.onlineshop.web.util.RequestUtil;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class DeleteCartServlet extends HttpServlet {
    private final SecurityService securityService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int productId = Integer.parseInt(request.getParameter("id"));
        String token = RequestUtil.getToken(request); // todo get from session ?

        securityService.removeFromCart(productId, token);  // todo which service process this??
        response.sendRedirect("/products/cart");
    }
}
