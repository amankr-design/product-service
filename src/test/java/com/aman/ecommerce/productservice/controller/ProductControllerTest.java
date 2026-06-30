package com.aman.ecommerce.productservice.controller;

import com.aman.ecommerce.productservice.dto.ProductDto;
import com.aman.ecommerce.productservice.security.JwtAuthenticationFilter;
import com.aman.ecommerce.productservice.service.ProductService;
import com.aman.ecommerce.productservice.auth.*;
import com.aman.ecommerce.productservice.auth.JwtService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.*;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doNothing;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private ProductService productService;

    @Test
    void testSaveProduct() throws Exception{

        ProductDto product = new ProductDto();

        product.setName("iPhone 16");
        product.setDescription("Apple Mobile");
        product.setPrice(80000.0);
        product.setQuantity(10);

        when(productService.saveProduct(any(ProductDto.class))).thenReturn(product);

        mockMvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("iPhone 16"))
                .andExpect(jsonPath("$.price").value(80000.0));
    }

    @Test
    void testGetAllProducts() throws Exception{

        ProductDto product = new ProductDto();

        product.setId(1L);
        product.setName("iPhone 16");
        product.setDescription("Apple Mobile");
        product.setPrice(80000.0);
        product.setQuantity(10);

        when(productService.getAllProducts()).thenReturn(List.of(product));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("iPhone 16"))
                .andExpect(jsonPath("$[0].price").value(80000.0));
    }

    @Test
    void testGetProductById() throws Exception{

        ProductDto product = new ProductDto();

        product.setId(1L);
        product.setName("iPhone 16");
        product.setDescription("Apple Mobile");
        product.setPrice(80000.0);
        product.setQuantity(10);

        when(productService.getProductById(1L)).thenReturn(product);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("iPhone 16"))
                .andExpect(jsonPath("$.price").value(80000.0));
    }

    @Test
    void testUpdateProduct() throws Exception{

        ProductDto product = new ProductDto();

        product.setId(1L);
        product.setName("iPhone 16 Pro");
        product.setDescription("Apple Mobile");
        product.setPrice(90000.0);
        product.setQuantity(15);

        when(productService.updateProduct(eq(1L), any(ProductDto.class))).thenReturn(product);

        mockMvc.perform(put("/api/products/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("iPhone 16 Pro"))
                .andExpect(jsonPath("$.price").value(90000.0))
                .andExpect(jsonPath("$.quantity").value(15));
    }

    @Test
    void testDeleteProduct() throws Exception{

        doNothing().when(productService).deleteProduct(1L);

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isOk());

        verify(productService, times(1)).deleteProduct(1L);
    }
}
