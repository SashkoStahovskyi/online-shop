package com.stahovskyi.onlineshop.web.security.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Credentials {

    private String username;
    private String password;
}
