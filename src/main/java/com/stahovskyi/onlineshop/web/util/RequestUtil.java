package com.stahovskyi.onlineshop.web.util;

import com.stahovskyi.onlineshop.entity.Credentials;
import com.stahovskyi.onlineshop.entity.Session;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Objects;
import java.util.Optional;

import static com.stahovskyi.onlineshop.configuration.PropertiesReader.getLocalProperties;

public class RequestUtil {
    private static final String USER_TOKEN = getLocalProperties().getProperty("user.token");

    public static String getRequestToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (Objects.nonNull(cookies)) {
            for (Cookie cookie : cookies) {
                if (USER_TOKEN.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static String getSessionToken(HttpServletRequest request) {
        Session session = (Session) request.getAttribute("session");
        return session.getToken();
    }

    public static Optional<Credentials> getCredentials(HttpServletRequest request) {

        return Optional.ofNullable(Credentials.builder()
                .userName(request.getParameter("username"))
                .password(request.getParameter("password"))
                .build());
    }

    public static int getProductId(HttpServletRequest request) {
        return Integer.parseInt(request.getParameter("id"));
    }

}
