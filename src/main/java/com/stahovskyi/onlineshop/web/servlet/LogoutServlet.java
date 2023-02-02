package com.stahovskyi.onlineshop.web.servlet;

import com.stahovskyi.onlineshop.service.SecurityService;
import com.stahovskyi.onlineshop.web.security.entity.Session;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class LogoutServlet extends HttpServlet {
    private final SecurityService securityService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Session session = (Session) request.getAttribute("session");
        String token = session.getToken(); // todo Use one concept when get value token from session and check other classes
        securityService.logout(token);
        log.info(" Remove session !");
        response.sendRedirect("/registration"); // todo -> which variant is better??

        /*if (securityService.isLogout(token)) {
            log.info(" Remove session !");
            response.sendRedirect("/registration");

        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, " logout failed! session for token not exist!");
        }*/
    }

}
