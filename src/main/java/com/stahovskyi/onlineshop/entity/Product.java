
package com.stahovskyi.onlineshop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class Product {

    private int id;
    private String name;
    private double price;
    private LocalDate date;
    private String description;
}

