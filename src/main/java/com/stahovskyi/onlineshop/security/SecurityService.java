package com.stahovskyi.onlineshop.security;

import com.stahovskyi.onlineshop.entity.Credentials;
import com.stahovskyi.onlineshop.entity.User;
import com.stahovskyi.onlineshop.service.UserService;
import com.stahovskyi.onlineshop.util.PasswordEncoder;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class SecurityService {
    private static final List<String> userTokenList = new ArrayList<>(); // todo --> need threads safe list
    private static final String USER_TOKEN = "user-token";
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;


    public String login(Credentials credentials) {
        Optional<User> user = userService.getUser(credentials);

        if (user.isPresent()) {
            User userFromDb = user.get();
            String hashedPassword = userFromDb.getPassword();
            String salt = userFromDb.getSalt();
            String hash = passwordEncoder.generateHash(credentials.getPassword(), salt);

            if (hashedPassword.equals(hash)) {
                log.info(" User credentials have been successfully authenticated !");
                return generateToken();
            }
        }
        String salt = passwordEncoder.generateSalt();
        String hashedPassword = passwordEncoder.generateHash(credentials.getPassword(), salt);
        userService.save(credentials, hashedPassword, salt);
        log.info(" Save new user credentials !");
        return generateToken();
    }

    private String generateToken() {
        String uuid = UUID.randomUUID().toString();
        userTokenList.add(uuid);
        log.info(" Create new token !");
        return uuid;
    }

    public boolean isTokenValid(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (USER_TOKEN.equals(cookie.getName())) {
                    if (userTokenList.contains(cookie.getValue())) {
                        log.info(" Client cookie is authorize successfully !");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // todo here need finish
    enum Session {

    }


}
