
package com.stahovskyi.onlineshop.service;

import com.stahovskyi.onlineshop.dao.ProductDao;
import com.stahovskyi.onlineshop.entity.Product;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ProductService {
    private final ProductDao productDao;

    public List<Product> getAll() {
        return productDao.findAll();
    }

    public void addProduct(Product product) {
        productDao.add(product);
    }

    public void delete(int id) {
        productDao.delete(id);
    }

    public void edit(Product product) {
        productDao.update(product);
    }

    public Optional<Product> getById(int id) {
        return productDao.getById(id);
    }

    public List<Product> search(String name) {
        return productDao.search(name);
    }
}

