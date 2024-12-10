package com.example.demo.service;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {
    void saveProduct(Product product);
    Page<Product> getProducts(Pageable pageable);
    Page<Product> getProductsByName(String name, Pageable pageable);
    void deleteProductById(Long id);
    Page<Product> getProductsByCategory(Category category, Pageable pageable);
}
