package com.aman.ecommerce.productservice.service;

import com.aman.ecommerce.productservice.ProductServiceApplication;
import com.aman.ecommerce.productservice.dto.ProductDto;
import com.aman.ecommerce.productservice.entity.Product;


import com.aman.ecommerce.productservice.exception.ProductNotFoundException;
import com.aman.ecommerce.productservice.repository.ProductRepository;
import lombok.Lombok;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void testCreateProduct(){

        ProductDto productDto = new ProductDto();

        productDto.setName("iPhone 16");
        productDto.setDescription("Apple Mobile");
        productDto.setPrice(80000.0);
        productDto.setQuantity(10);

        Product product = new Product();

        product.setName("iPhone 16");
        product.setDescription("Apple Mobile");
        product.setPrice(80000.0);
        product.setQuantity(10);

        when(modelMapper.map(productDto, Product.class)).thenReturn(product);

        when(productRepository.save(product)).thenReturn(product);

        when(modelMapper.map(product, ProductDto.class)).thenReturn(productDto);

        ProductDto savedProduct = productService.saveProduct(productDto);

        assertEquals("iPhone 16", savedProduct.getName());
        assertEquals("Apple Mobile", savedProduct.getDescription());
        assertEquals(80000.0, savedProduct.getPrice());
        assertEquals(10, savedProduct.getQuantity());

        verify(productRepository, times(1)).save(product);

    }

    @Test
    void testGetProductById(){

        Long productId = 1L;

        Product product = new Product();

        product.setId(productId);
        product.setName("iPhone 16");
        product.setDescription("Apple Mobile");
        product.setPrice(80000.0);
        product.setQuantity(10);

        ProductDto productDto = new ProductDto();

        productDto.setId(productId);
        productDto.setName("iPhone 16");
        productDto.setDescription("Apple Mobile");
        productDto.setPrice(80000.0);
        productDto.setQuantity(10);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        when(modelMapper.map(product, ProductDto.class)).thenReturn(productDto);

        ProductDto result = productService.getProductById(productId);

        assertEquals(productId, result.getId());
        assertEquals(productId, result.getId());
        assertEquals(productId, result.getId());
        assertEquals(productId, result.getId());
        assertEquals(productId, result.getId());

    }

    @Test
    void testUpdateProduct(){

        Long productId = 1L;

        Product  existingProduct = new Product();

        existingProduct.setId(productId);
        existingProduct.setName("iPhone 15");
        existingProduct.setDescription("Old Apple Mobile");
        existingProduct.setPrice(70000.0);
        existingProduct.setQuantity(5);

        ProductDto updateDto = new ProductDto();

        updateDto.setName("iPhone 16");
        updateDto.setDescription("Apple Mobile");
        updateDto.setPrice(80000.0);
        updateDto.setQuantity(10);


        Product updateProduct = new Product();

        updateProduct.setName("iPhone 16");
        updateProduct.setDescription("Apple Mobile");
        updateProduct.setPrice(80000.0);
        updateProduct.setQuantity(10);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        when(productRepository.save(existingProduct)).thenReturn(updateProduct);

        when(modelMapper.map(updateProduct, ProductDto.class)).thenReturn(updateDto);

        ProductDto result = productService.updateProduct(productId, updateDto);

        assertEquals("iPhone 16", result.getName());
        assertEquals("Apple Mobile", result.getDescription());
        assertEquals(80000.0, result.getPrice());
        assertEquals(10, result.getQuantity());

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository,times(1)).save(existingProduct);

    }

    @Test
    void testDeleteProduct(){

        Long productId = 1L;

        Product product = new Product();

        product.setId(productId);
        product.setName("iPhone 16");

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        doNothing().when(productRepository).delete(product);

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).delete(product);

    }

    @Test
    void testGetProductById_NotFound(){

        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {

            productService.getProductById(productId);

        });

        verify(productRepository).findById(productId);
    }

    @Test
    void testUpdateProduct_NotFound(){

        Long productId = 1L;

        ProductDto dto = new ProductDto();

        dto.setName("iPhone i6");

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {

            productService.updateProduct(productId, dto);
        });

        verify(productRepository).findById(productId);
    }

    @Test
    void testDeleteProduct_NotFound(){

        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {

            productService.deleteProduct(productId);

        });

        verify(productRepository).findById(productId);
    }
}
