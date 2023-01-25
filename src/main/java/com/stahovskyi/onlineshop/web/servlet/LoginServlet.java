
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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
public class LoginServlet extends HttpServlet {

    private final PageGenerator pageGenerator = PageGenerator.instance();
    private final SecurityService securityService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String page = pageGenerator.getPage("log_in.html", new HashMap<>());
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Credentials credentials = CredentialsUtil.getCredentials(request); // todo -> if Null ??
        Session session = securityService.login(credentials);

        if (Objects.nonNull(session)) {
            Cookie cookie = new Cookie("user-token", session.getToken());
            cookie.setMaxAge(PropertiesReader.getCookieAge());
            response.addCookie(cookie);                      // todo  -> ERRORS in Filter
            response.sendRedirect("/products");

        } else {
            // here response error with link to registration page or error below
            //  response.sendError(HttpServletResponse.SC_FORBIDDEN, "Check syntax or create new account");
            response.sendRedirect("/registration");
        }
    }


}
