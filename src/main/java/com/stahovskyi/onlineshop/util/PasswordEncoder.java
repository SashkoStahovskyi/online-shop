package com.stahovskyi.onlineshop.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

public class PasswordEncoder {

    public String createEncryption(String userPassword) {
        return generateHash(userPassword) + salt;
    }

    private String generateSalt() {
        return UUID.randomUUID().toString();
    }

    private String generateHash(String password) {
        return DigestUtils.md2Hex(password);
    }

}
