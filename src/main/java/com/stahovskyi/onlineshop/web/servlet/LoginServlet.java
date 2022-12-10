
package com.stahovskyi.onlineshop.web.servlet;

import com.stahovskyi.onlineshop.security.SecurityService;
import com.stahovskyi.onlineshop.util.PageGenerator;
import jakarta.servlet.ServletException;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = pageGenerator.getPage("log_in.html", new HashMap<>());
        response.getWriter().write(page);
    }

    @Override                                                           //todo --> in filter for all exceptions check
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("user-name");
        String password = request.getParameter("password");
        String token = securityService.login(username, password);

        if (token != null) {
            response.addCookie(new Cookie("user-token", token));
            response.sendRedirect("/products");
        } else
            response.sendRedirect("/login");

      /*  if (securityService.isUserExist(login, password)) {
            log.info("User exist in DB !");
            response.addCookie(securityService.generateToken());
            response.sendRedirect("/products");

        } else
            securityService.login(user);
        log.info("Save user to DB !");

        response.addCookie(securityService.generateToken());
        log.info("Send redirect to  --> /products !");
        response.sendRedirect("/products");*/

        // securityService.login return into loginServlet token
        // add Max Age for cookie

    }
}
