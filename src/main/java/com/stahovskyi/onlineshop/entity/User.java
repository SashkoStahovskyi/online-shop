package com.stahovskyi.onlineshop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private int id;
    private String userName;
    private String hashedPassword;
    private String salt;
    private Role role;

    public enum Role {
        GUEST, USER, ADMIN
    }

}
