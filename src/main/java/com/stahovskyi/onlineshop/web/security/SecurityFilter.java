package com.stahovskyi.onlineshop.web.security;

import com.stahovskyi.onlineshop.service.SecurityService;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@AllArgsConstructor
public class SecurityFilter implements Filter {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private SecurityService securityService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (securityService.isCookieValid(httpServletRequest.getCookies())) {
            chain.doFilter(request, response);
        }
        httpServletResponse.sendRedirect("/login");
        LOGGER.info("Cookie not valid --> sand redirect to Login_page !");
    }
}
