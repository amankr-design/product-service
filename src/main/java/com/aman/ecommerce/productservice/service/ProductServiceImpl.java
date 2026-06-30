package com.aman.ecommerce.productservice.service;


import com.aman.ecommerce.productservice.dto.ProductDto;
import com.aman.ecommerce.productservice.entity.Product;
import com.aman.ecommerce.productservice.exception.ProductNotFoundException;
import com.aman.ecommerce.productservice.repository.ProductRepository;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import org.slf4j.Logger;

@Service
public class ProductServiceImpl implements ProductService {

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);


    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper){

        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductDto saveProduct(ProductDto productDto) {

        logger.info("Saving new product: {}", productDto.getName());

        Product product = convertToEntity(productDto);
        Product savedProduct = productRepository.save(product);

        logger.info("Product saved successfully with id: {}", savedProduct.getId());

        return
                convertToDto(savedProduct);
    }

    @Override
    public List<ProductDto> getAllProducts() {

        logger.info("Fetching all products");

        List<Product> products = productRepository.findAll();

        logger.info("Total products found: {}", products.size());

        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public List<ProductDto>getAllProducts(int page, int size, String sortBy, String direction) {

        logger.info("Fetching product. page: {}, Size: {}", page, size);

        Sort sort;

        if (direction.equalsIgnoreCase("asc")){
            sort = Sort.by(sortBy).ascending();
        }else {

            sort = Sort.by(sortBy).descending();
        }
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> productPage = productRepository.findAll(pageable);

        return productPage.getContent().stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDto getProductById(Long id) {

        logger.info("Fetching product with id: {}", id);

        Product product = productRepository.findById(id).orElseThrow(() -> new
                ProductNotFoundException("Product not found with id : " + id));

        logger.info("Product found: {} ", product.getName());

        return convertToDto(product);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {

        logger.info("Updating product with id: {}", id);

        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new
                ProductNotFoundException("Product not found with id : " + id));


            existingProduct.setName(productDto.getName());

            existingProduct.setDescription(productDto.getDescription());

            existingProduct.setPrice(productDto.getPrice());

            existingProduct.setQuantity(productDto.getQuantity());

            Product updatedProduct = productRepository.save(existingProduct);

            logger.info("Product update successfully");

            return convertToDto(updatedProduct);

    }

    @Override
    public void deleteProduct(Long id) {

        logger.info("Deleting product with id: {}", id);

        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        productRepository.delete(product);

        logger.info("Product deleted successfully");

    }

    private ProductDto convertToDto(Product product){

        return modelMapper.map(product, ProductDto.class);
    }

    private Product convertToEntity(ProductDto productDto){

        return modelMapper.map(productDto, Product.class);

    }

    @Override
    public List<ProductDto> searchProducts(String name){

        logger.info("Searching products with name: {}", name);

        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);

        logger.info("Total Products found: {} ", products.size());

        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public List<ProductDto>filterProducts(String name, Double minPrice, Double maxPrice, Integer minQuantity, Integer maxQuantity){

        logger.info("Filtering products between...");

        List<Product> products = productRepository.findByNameContainingIgnoreCaseAndPriceBetweenAndQuantityBetween(name, minPrice, maxPrice, minQuantity, maxQuantity);

        return products.stream().map(this::convertToDto).toList();
    }

}