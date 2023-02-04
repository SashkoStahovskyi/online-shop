package com.stahovskyi.onlineshop.web.servlet;

import com.stahovskyi.onlineshop.service.SecurityService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static com.stahovskyi.onlineshop.web.util.RequestUtil.getProductId;
import static com.stahovskyi.onlineshop.web.util.RequestUtil.getSessionToken;

@Slf4j
@RequiredArgsConstructor
public class DeleteCartServlet extends HttpServlet {
    private final SecurityService securityService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = getSessionToken(request);
        int productId = getProductId(request);

        securityService.removeFromCart(productId, token);
        response.sendRedirect("/products/cart");
    }
}
