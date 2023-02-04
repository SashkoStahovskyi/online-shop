package com.stahovskyi.onlineshop.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

public class PasswordEncoder {

    public static String generateHash(String password, String salt) {
        return DigestUtils.md2Hex(password + salt);
    }

    public static String generateSalt() {
        return UUID.randomUUID().toString();
    }

}
