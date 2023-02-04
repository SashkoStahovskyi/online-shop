package com.stahovskyi.onlineshop.web.servlet;

import com.stahovskyi.onlineshop.entity.Credentials;
import com.stahovskyi.onlineshop.entity.Session;
import com.stahovskyi.onlineshop.service.SecurityService;
import com.stahovskyi.onlineshop.util.PageGenerator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.HashMap;

import static com.stahovskyi.onlineshop.configuration.PropertiesReader.getLocalProperties;
import static com.stahovskyi.onlineshop.web.util.RequestUtil.getCredentials;

@RequiredArgsConstructor
public class RegisterServlet extends HttpServlet {
    private final PageGenerator pageGenerator = PageGenerator.instance();
    private final SecurityService securityService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter()
                .write(pageGenerator.getPage("registration.html", new HashMap<>()));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Credentials credentials = getCredentials(request)
                .orElseThrow(() -> new RuntimeException("You have not filled in the fields! Fill the fields correct please!"));

        Session session = securityService.save(credentials);
        Cookie cookie = new Cookie("user-token", session.getToken());
        cookie.setMaxAge(Integer
                .parseInt(getLocalProperties()
                        .getProperty("cookie.maxAge")));

        response.addCookie(cookie);
        response.sendRedirect("/products");
    }

}
