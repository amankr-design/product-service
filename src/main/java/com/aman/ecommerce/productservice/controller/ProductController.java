package com.aman.ecommerce.productservice.controller;

import com.aman.ecommerce.productservice.ProductServiceApplication;
import com.aman.ecommerce.productservice.dto.ProductDto;
import com.aman.ecommerce.productservice.entity.Product;
import com.aman.ecommerce.productservice.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.DoubleAccumulator;


@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;

    }

    @PostMapping
    public ProductDto saveProduct(@Valid @RequestBody ProductDto productDto){
        return
                productService.saveProduct(productDto);
    }

    @GetMapping("/test")
    public String test(){
        return "Spring Boot is working!";
    }

    @GetMapping
    public List<ProductDto> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id){

        return productService.getProductById(id);
    }

    @GetMapping("/page")
    public List<ProductDto> getProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam String sortBy
                                        ,@RequestParam String direction){

        return productService.getAllProducts(page, size, sortBy, direction);
    }

    @GetMapping("/search")
    public List<ProductDto> searchProducts(@RequestParam String name){
        return
        productService.searchProducts(name);
    }

    @GetMapping("/filter")
    public List<ProductDto>filterProducts(@RequestParam(defaultValue = "") String name, @RequestParam(defaultValue = "0") Double minPrice, @RequestParam(defaultValue = "999999") Double maxPrice, @RequestParam(defaultValue = "0") Integer minQuantity, @RequestParam(defaultValue = "999999") Integer maxQuantity)
    {

        return productService.filterProducts(name, minPrice, maxPrice, minQuantity, maxQuantity);

    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminOnly(){

        return "Welcome Admin";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String userOnly(){

        return "Welcome User";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(){

        return "Welcome Admin";
    }

    @GetMapping("user/dashboard")
    public String userDashboard(){

        return "Welcome User";
    }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDto productDto){

        return productService.updateProduct(id, productDto);

    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id){

        productService.deleteProduct(id);
        return "Product deleted successfully";
    }

}
