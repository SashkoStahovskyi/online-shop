package com.stahovskyi.onlineshop.web.security.entity;

import com.stahovskyi.onlineshop.entity.Product;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class Session {

    private String token;
    private LocalDateTime expireDate;
    private List<Product> cart;

}
