
package com.stahovskyi.onlineshop.web.servlet;

import com.stahovskyi.onlineshop.entity.Credentials;
import com.stahovskyi.onlineshop.entity.Session;
import com.stahovskyi.onlineshop.service.SecurityService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

import static com.stahovskyi.onlineshop.configuration.PropertiesReader.getLocalProperties;
import static com.stahovskyi.onlineshop.util.PageGenerator.getPageGeneratorInstance;
import static com.stahovskyi.onlineshop.web.util.RequestUtil.getCredentials;

@Slf4j
@AllArgsConstructor
public class LoginServlet extends HttpServlet {
    private final SecurityService securityService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter()
                .write(getPageGeneratorInstance()
                        .getPage("log_in.html", new HashMap<>()));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Credentials credentials = getCredentials(request)
                .orElseThrow(() -> new RuntimeException("You have not filled in the fields! Fill the fields correct please!"));

        Optional<Session> session = securityService.login(credentials);
        if (session.isPresent()) {
            Cookie cookie = new Cookie("user-token", session.get().getToken());
            cookie.setMaxAge(Integer
                    .parseInt(getLocalProperties()
                            .getProperty("cookie.maxAge")));

            response.addCookie(cookie);
            response.sendRedirect("/products");

        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Check syntax or create new account!");
        }
    }

}
