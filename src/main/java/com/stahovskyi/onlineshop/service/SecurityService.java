package com.stahovskyi.onlineshop.service;

import com.stahovskyi.onlineshop.configuration.PropertiesReader;
import com.stahovskyi.onlineshop.entity.Product;
import com.stahovskyi.onlineshop.entity.User;
import com.stahovskyi.onlineshop.security.PasswordEncoder;
import com.stahovskyi.onlineshop.web.security.entity.Credentials;
import com.stahovskyi.onlineshop.web.security.entity.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class SecurityService {
    private static final Map<String, Session> sessionList = new HashMap<>(); // todo --> need threads safe list
    private final PasswordEncoder passwordEncoder;
    private final ProductService productService;
    private final UserService userService;

    public Session save(Credentials credentials) {
        String salt = passwordEncoder.generateSalt();  // todo static or one static method
        String hashedPassword = passwordEncoder.generateHash(credentials.getPassword(), salt);

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

    public Session login(Credentials credentials) {
        Optional<User> user = userService.getUser(credentials);

        if (user.isPresent()) {  // todo realize with consumer ??
            User userFromDb = user.get(); // todo -> fix environment name for this method
            String hashedPassword = userFromDb.getHashedPassword();
            String salt = userFromDb.getSalt();

            String hash = passwordEncoder.generateHash(credentials.getPassword(), salt);

            if (hashedPassword.equals(hash)) {
                log.info(" User credentials have been successfully authenticated !");
                Session session = createSession(userFromDb);
                session.setUser(userFromDb);
                return session;
            }
        }
        return null;   // todo maybe possible write it better with optional??
    }

    public void addToCart(int productId, String token) { // todo -> do better with optional
        Session session = getSession(token);
        List<Product> cartList = session.getCart();

        Optional<Product> product = productService.getById(productId);

        if (product.isPresent()) {
            Product product1 = product.get();
            cartList.add(product1);
        }
    }

    public void removeFromCart(int productId, String token) {
        Session session = getSession(token);
        List<Product> productCart = session.getCart();
        productCart.removeIf(product -> product.getId() == productId);
        log.info(" remove product from product cart list!");
    }

    public boolean isValid(String token) {
        // true if exist token   // true if session for token exist // true if not expired
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
                .expireDate(LocalDateTime.now().plusSeconds(PropertiesReader.getCookieAge()))
                .cart(new ArrayList<>())
                .user(user)
                .build();

        sessionList.put(session.getToken(), session);
        log.info("Create new session for user: {}!", session.getUser().getUserName()); // todo StringFormat for user
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

