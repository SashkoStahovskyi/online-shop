package com.stahovskyi.onlineshop.web.security;

import com.stahovskyi.onlineshop.service.SecurityService;
import com.stahovskyi.onlineshop.web.security.entity.Session;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
public class SecurityFilter implements Filter {
    private final List<String> allowedPath = List.of("/login", "/registration");
    private static final String USER_TOKEN = "user-token";
    private final SecurityService securityService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String requestURI = httpServletRequest.getRequestURI();
        String token = getToken(httpServletRequest);

        if (allowedPath.contains(requestURI)) {
            log.info(" Allowed path! ");
            chain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        if (securityService.isValid(token)) {
            log.info(" User with valid token !");
            Session session = securityService.getSession(token);
            httpServletRequest.setAttribute("session", session);
            chain.doFilter(httpServletRequest, httpServletResponse);

        } else {
            log.info(" User not authorize! Redirect to registration page !");
            httpServletResponse.sendRedirect("/registration");
        }


    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private String getToken(HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (Objects.nonNull(cookies)) {
            for (Cookie cookie : cookies) {
                if (USER_TOKEN.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}





