package com.stahovskyi.onlineshop.web.servlet;

import com.stahovskyi.onlineshop.configuration.PropertiesReader;
import com.stahovskyi.onlineshop.service.SecurityService;
import com.stahovskyi.onlineshop.util.PageGenerator;
import com.stahovskyi.onlineshop.web.security.entity.Credentials;
import com.stahovskyi.onlineshop.web.security.entity.Session;
import com.stahovskyi.onlineshop.web.util.CredentialsUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.HashMap;

@RequiredArgsConstructor
public class RegisterServlet extends HttpServlet {
    private final PageGenerator pageGenerator = PageGenerator.instance();
    private final SecurityService securityService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String page = pageGenerator.getPage("registration.html", new HashMap<>());
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Credentials credentials = CredentialsUtil.getCredentials(request);
        Session session = securityService.save(credentials);

        Cookie cookie = new Cookie("user-token", session.getToken());
        cookie.setMaxAge(PropertiesReader.getCookieAge());
        response.addCookie(cookie);
        response.sendRedirect("/products");

        // todo  -> ERRORS with issue if Same username.
        // 500 -> user already exist

       /* String page = pageGenerator.getPage("registration_response.html", new HashMap<>());
        response.getWriter().write(page);*/
    }

}
