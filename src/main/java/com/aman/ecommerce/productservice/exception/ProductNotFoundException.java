package com.aman.ecommerce.productservice.exception;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(String message){

        super(message);
    }
}
