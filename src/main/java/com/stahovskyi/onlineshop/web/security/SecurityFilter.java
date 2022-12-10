package com.stahovskyi.onlineshop.web.security;

import com.stahovskyi.onlineshop.security.SecurityService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class SecurityFilter implements Filter {
    private final SecurityService securityService;
    private final List<String> allowedPath = List.of("/login");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String requestURI = httpServletRequest.getRequestURI();

        for (String allowedPath : allowedPath) {
            if (requestURI.startsWith(allowedPath)) {
                chain.doFilter(request, response);
                return;
            }
        }

        if (securityService.isTokenValid(httpServletRequest.getCookies())) {
            chain.doFilter(request, response);
            log.info("User token is authorized !!");

        } else {
            httpServletResponse.sendRedirect("/login");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

}
