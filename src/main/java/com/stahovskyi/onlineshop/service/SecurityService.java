package com.stahovskyi.onlineshop.service;

import jakarta.servlet.http.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SecurityService {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private static final List<String> sessionList = new ArrayList<>();

    public Cookie generateCookies() {
        String uuid = UUID.randomUUID().toString();
        sessionList.add(uuid);
        LOGGER.info("Create new cookie for user !");
        return new Cookie("user-token", uuid);
    }

    public boolean isCookieValid(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("user-token".equals(cookie.getName())) {
                    if (sessionList.contains(cookie.getValue())) {
                        LOGGER.info("Cookie is valid !");
                        return true;
                    }
                }
            }
        }
        return false;
    }
    // todo : validation method for user
}
