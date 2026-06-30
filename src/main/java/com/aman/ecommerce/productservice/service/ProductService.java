package com.aman.ecommerce.productservice.service;

import com.aman.ecommerce.productservice.dto.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto saveProduct(ProductDto productDto);

    List<ProductDto> getAllProducts();

    List<ProductDto> getAllProducts(int page, int size, String sortBy, String direction);

    ProductDto getProductById(Long id);

    ProductDto updateProduct(Long id, ProductDto productDto);

    void deleteProduct(Long id);

    List<ProductDto> searchProducts(String name);

    List<ProductDto> filterProducts(String name, Double minPrice, Double maxPrice, Integer minQuantity, Integer maxQuantity);
}
