package com.stahovskyi.onlineshop.web.servlet;

import com.stahovskyi.onlineshop.entity.Session;
import com.stahovskyi.onlineshop.service.SecurityService;
import com.stahovskyi.onlineshop.util.PageGenerator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;

import static com.stahovskyi.onlineshop.web.util.RequestUtil.getProductId;
import static com.stahovskyi.onlineshop.web.util.RequestUtil.getRequestToken;


@Slf4j
@RequiredArgsConstructor
public class CartServlet extends HttpServlet {
    private final PageGenerator pageGenerator = PageGenerator.instance();
    private final SecurityService securityService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Session session = securityService.getSession(getRequestToken(request));

        HashMap<String, Object> pageData = new HashMap<>();
        pageData.put("cartProducts", session.getCart());
        pageData.put("userName", session.getUser().getUserName());
        pageData.put("userRole", session.getUser().getRole());

        response.getWriter()
                .write(pageGenerator.getPage("cart.html", pageData));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        securityService.addToCart(getProductId(request), getRequestToken(request));

        log.info(" add product to cart! ");
        response.sendRedirect("/products");
    }

}
