package com.stahovskyi.onlineshop.dao;

import com.stahovskyi.onlineshop.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {

    List<Product> findAll();

    void add(Product product);

    void delete(int id);

    void update(Product product);

    Optional<Product> getById(int id);

   List<Product> search(String name);
}
