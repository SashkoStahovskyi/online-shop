package com.stahovskyi.onlineshop.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class Session {

    private String token;
    private LocalDateTime expireDate;
    private List<Product> cart;
    private User user;

}
