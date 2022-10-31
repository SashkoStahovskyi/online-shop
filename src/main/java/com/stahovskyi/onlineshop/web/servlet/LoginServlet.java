
package com.stahovskyi.onlineshop.web.servlet;

import com.stahovskyi.onlineshop.entity.User;
import com.stahovskyi.onlineshop.service.SecurityService;
import com.stahovskyi.onlineshop.service.UserService;
import com.stahovskyi.onlineshop.web.mapper.UserRequestMapper;
import com.stahovskyi.onlineshop.web.util.PageGenerator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;

@AllArgsConstructor
public class LoginServlet extends HttpServlet {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final PageGenerator pageGenerator = PageGenerator.instance();

    private final UserService userService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String page = pageGenerator.getPage("log_in.html", new HashMap<>());
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // todo : check user in DB if not create new User and give him cookie ! if ok do redirect

        User user = UserRequestMapper.toUser(request);
        userService.save(user);
        LOGGER.info("Save user to DB !");

    }
}
