package com.aman.ecommerce.productservice.repository;

import com.aman.ecommerce.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product>findByPriceBetween(Double minPrice, Double maxPrice);

    List<Product>findByNameContainingIgnoreCaseAndPriceBetweenAndQuantityBetween(String name, Double minPrice, Double maxPrice, Integer minQuantity, Integer manQuantity);
}
