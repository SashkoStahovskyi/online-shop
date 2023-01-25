package com.stahovskyi.onlineshop.web.util;

import com.stahovskyi.onlineshop.web.security.entity.Credentials;
import jakarta.servlet.http.HttpServletRequest;

public class CredentialsUtil {

    public static Credentials getCredentials(HttpServletRequest request) {

        return Credentials.builder()
                .username(request.getParameter("username"))
                .password(request.getParameter("password"))
                .build();
    }
}
