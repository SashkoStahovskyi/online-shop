package com.stahovskyi.onlineshop.web.security;

import com.stahovskyi.onlineshop.security.SecurityService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class SecurityFilter implements Filter {
    private final List<String> allowedPath = List.of("/login");
    private final SecurityService securityService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String requestURI = httpServletRequest.getRequestURI();

        // todo do normal validation !
        for (String allowedPath : allowedPath) {
            if (requestURI.startsWith(allowedPath)) {
                log.info(" Allowed path to login page !");
                chain.doFilter(request, response);
                return;
            }
        }

        if (securityService.isTokenValid(httpServletRequest.getCookies())) {
            log.info(" User with valid token !");
            chain.doFilter(request, response);

        } else {
            log.info(" User not authorize! Redirect to login page !");
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





