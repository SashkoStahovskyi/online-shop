package com.stahovskyi.onlineshop.web.servlet.filter;

import com.stahovskyi.onlineshop.entity.Session;
import com.stahovskyi.onlineshop.entity.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
public class AuthorizationFilter implements Filter {
    private final List<String> userAllowedPath = List.of("/products", "/products/search", "/products/cart",
            "/cart/delete", "/logout");
    private final List<String> guestAllowedPath = List.of("/products", "/products/search", "/logout");
    private final List<String> allowedPath = List.of("/login", "/registration");


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String requestURI = httpServletRequest.getRequestURI();

        if (allowedPath.contains(requestURI)) {
            log.info(" Allowed path to login or registration page! ");
            chain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        Session session = (Session) httpServletRequest.getAttribute("session");
        User.Role role = session.getUser().getRole();

        if (User.Role.ADMIN == role) {
            log.info(" Allowed path for " + role);
            chain.doFilter(httpServletRequest, httpServletResponse);

        } else if (User.Role.USER == role && userAllowedPath.contains(requestURI)) {
            log.info(" Allowed path for " + role);
            chain.doFilter(httpServletRequest, httpServletResponse);

        } else if (User.Role.GUEST == role && guestAllowedPath.contains(requestURI)) {
            log.info(" Allowed path for " + role);
            chain.doFilter(httpServletRequest, httpServletResponse);

        } else {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "sorry" + role + "!.You have limitations request of resource!");
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
