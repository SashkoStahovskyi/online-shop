package com.stahovskyi.onlineshop.web.servlet;

import com.stahovskyi.onlineshop.service.SecurityService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static com.stahovskyi.onlineshop.web.util.RequestUtil.getSessionToken;

@Slf4j
@RequiredArgsConstructor
public class LogoutServlet extends HttpServlet {
    private final SecurityService securityService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        securityService.logout(getSessionToken(request));
        log.info("Remove session!");
        response.sendRedirect("/registration");
    }
}
