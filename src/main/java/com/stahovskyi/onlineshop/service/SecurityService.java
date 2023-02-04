package com.stahovskyi.onlineshop.service;

import com.stahovskyi.onlineshop.entity.Credentials;
import com.stahovskyi.onlineshop.entity.Product;
import com.stahovskyi.onlineshop.entity.Session;
import com.stahovskyi.onlineshop.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.stahovskyi.onlineshop.configuration.PropertiesReader.getLocalProperties;
import static com.stahovskyi.onlineshop.util.PasswordEncoder.generateHash;
import static com.stahovskyi.onlineshop.util.PasswordEncoder.generateSalt;

@Slf4j
@RequiredArgsConstructor
public class SecurityService {
    private static final Map<String, Session> sessionList = Collections.synchronizedMap(new HashMap<>());
    private final ProductService productService;
    private final UserService userService;

    public Session save(Credentials credentials) {
        String salt = generateSalt();
        String hashedPassword = generateHash(credentials.getPassword(), salt);

        User user = User.builder()
                .salt(salt)
                .userName(credentials.getUserName())
                .hashedPassword(hashedPassword)
                .build();

        userService.save(user);
        Session session = createSession(user);
        session.setUser(user);

        return session;
    }

    public Optional<Session> login(Credentials credentials) {
        Optional<User> user = userService.getUser(credentials);

        if (user.isPresent()) {
            User userFromDb = user.get();
            String hashedPassword = userFromDb.getHashedPassword();
            String salt = userFromDb.getSalt();
            String hash = generateHash(credentials.getPassword(), salt);

            if (hashedPassword.equals(hash)) {
                log.info(" User credentials have been successfully authenticated !");
                Session session = createSession(userFromDb);
                session.setUser(userFromDb);

                return Optional.of(session);
            }
        }
        return Optional.empty();
    }

    public void addToCart(int productId, String token) {
        Session session = getSession(token);
        List<Product> cartList = session.getCart();

        Product product = productService.getById(productId).orElseThrow();
        cartList.add(product);
    }

    public void removeFromCart(int productId, String token) {
        Session session = getSession(token);
        List<Product> productCart = session.getCart();
        productCart.removeIf(product -> product.getId() == productId);
        log.info(" remove product from product cart list!");
    }

    public boolean isValid(String token) {
        if (Objects.nonNull(token) && isSessionExist(token) && isSessionNotExpired(token)) {
            log.info(" Valid session with token exist in session list !");
            return true;
        }
        return false;
    }

    public void logout(String token) {
        sessionList.remove(token);
    }

    public Session getSession(String token) {
        return sessionList.get(token);
    }

    private Session createSession(User user) {
        Session session = Session.builder()
                .token(generateToken())
                .expireDate(LocalDateTime.now().plusSeconds(Long.parseLong(getLocalProperties().getProperty("cookie.maxAge"))))
                .cart(new ArrayList<>())
                .user(user)
                .build();

        sessionList.put(session.getToken(), session);
        log.info("Create new session for user: {}!", session.getUser().getUserName());
        return session;
    }

    private boolean isSessionNotExpired(String token) {
        Session session = sessionList.get(token);
        if (session.getExpireDate().isBefore(LocalDateTime.now())) {
            sessionList.remove(token);
            log.info(" remove session with expired time from session list !");
            return false;
        }
        return true;
    }

    private boolean isSessionExist(String token) {
        return sessionList.containsKey(token);
    }

    private String generateToken() {
        log.info("Generate new token !");
        return UUID.randomUUID().toString();
    }

}

