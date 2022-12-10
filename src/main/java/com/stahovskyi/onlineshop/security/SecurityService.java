package com.stahovskyi.onlineshop.security;

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
public class SecurityService { // задача отримати юзера , для цього йоиму потрібен UserService ( а він вже отримує юєерів
    // з різних сервісів
    // SecService не знає про куки але генерує токен , сервлет працює зкукками і присвоює значення токена кукам

    private static final List<String> userTokenList = new ArrayList<>(); // todo --> need threads safe list

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    // todo : generated salt for unique password

    // todo : check password

   public String login(String username, String password) {

        Optional<User> userFromDb = userService.getUser(username);
        if (userFromDb.isPresent()) {
            User user = userFromDb.get();
            String passwordFromDb = user.getPassword();
            String saltFromDb = user;
            String hash = passwordEncoder.createEncryption(passwordFromDb);
            String userHash = passwordEncoder.createEncryption()
        }



        //String encryptedPassword = passwordEncoder.createEncryption(password);



        // todo --> get user from db
        // todo --> encryptPassword
        // todo --> original password from db -> hash( password) -> hashed Password
        // todo --> compare (hashed password from UI & from db)
       return null;
   }

    private String generateToken() {
        String uuid = UUID.randomUUID().toString();
        userTokenList.add(uuid);
        return uuid;
    }

    public boolean isTokenValid(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    if (userTokenList.contains(cookie.getValue())) {
                        log.info("Cookie is authorize !");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    enum Session { // todo here need finish

    }


}
