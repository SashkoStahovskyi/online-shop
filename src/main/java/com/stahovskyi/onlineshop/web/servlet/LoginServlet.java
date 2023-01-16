
package com.stahovskyi.onlineshop.web.servlet;

import com.stahovskyi.onlineshop.entity.Credentials;
import com.stahovskyi.onlineshop.security.SecurityService;
import com.stahovskyi.onlineshop.util.PageGenerator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;

@Slf4j
@AllArgsConstructor
public class LoginServlet extends HttpServlet {
    private final PageGenerator pageGenerator = PageGenerator.instance();
    private final SecurityService securityService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String page = pageGenerator.getPage("log_in.html", new HashMap<>());
        response.getWriter().write(page);       // todo - login page do better
    }

    @Override                                            //todo --> in filter for all exceptions check
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
       Credentials credentials = Credentials.builder()
               .username(request.getParameter("username"))
               .password(request.getParameter("password"))
               .build();

        String token = securityService.login(credentials);

        response.addCookie(new Cookie("user-token", token)); // todo add max age for cookie
        response.sendRedirect("/products");
    }

}
