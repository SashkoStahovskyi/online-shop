package com.stahovskyi.onlineshop.service;

import com.stahovskyi.onlineshop.security.PasswordEncoder;
import com.stahovskyi.onlineshop.web.security.entity.Credentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class SecurityService {
    // private static final Map<String, Session> sessionList = new HashMap<>(); // todo --> need threads safe list

    private static final List<String> userTokenList = new ArrayList<>();

    private static final String USER_TOKEN = "user-token"; // todo need??

    private static final int COOKIE_AGE = 7200;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public String save(Credentials credentials) {
        String salt = passwordEncoder.generateSalt();  // todo static or one static method
        String hashedPassword = passwordEncoder.generateHash(credentials.getPassword(), salt);
        userService.save(credentials, hashedPassword, salt);
        // return createSession();
        String token = generateToken();
        userTokenList.add(token);
        return token;
    }

  /*  public Session login(Credentials credentials) {
        Optional<User> user = userService.getUser(credentials);

        if (user.isPresent()) {
            User userFromDb = user.get();
            String hashedPassword = userFromDb.getPassword();
            String salt = userFromDb.getSalt();
            String hash = passwordEncoder.generateHash(credentials.getPassword(), salt);

            if (hashedPassword.equals(hash)) {
                log.info(" User credentials have been successfully authenticated !");
                return createSession();
            }
        }
        return null;   // todo maybe possible write it better with optional??
    }
*/
  /*  private Session createSession() {
        Session session = Session.builder()
                .token(generateToken())
                .expireDate(LocalDateTime.now().plusSeconds(COOKIE_AGE))
                .cart(new ArrayList<>())
                .build();

        sessionList.put(session.getToken(), session);
        log.info(" Create new session and add to session list! ");
        return session;
    }*/

    public boolean isValid(String token) {

        // check if exist session for this token in session list
        if (Objects.nonNull(token)) {
            // Session session = getSession(token);
            for (String t : userTokenList) {
                if (token.equals(t)) {
                    return true;
                }
            }
           /* if (token.equals(session.getToken())) {
                return true;
            }*/
        }
        return false;
    }

  /*  public Session getSession(String token) {
        return sessionList.get(token);
    }*/

    private String generateToken() {
        log.info("Generate new token !");
        return UUID.randomUUID().toString();
    }

}

   /* public boolean isValid(HttpServletRequest request) {  // todo -> use stream for session
        Cookie[] cookies = request.getCookies();
        if (Objects.nonNull(cookies)) {
            for (Cookie cookie : cookies) {
                if (USER_TOKEN.equals(cookie.getName())) {
                  *//*  Session session = getSession(cookie.getValue());
                    request.setAttribute("session", session);*//*
                    log.info(" Client token is authorize successfully!");
                    return true;
                }
            }
        }
        return false;
    }
*/

