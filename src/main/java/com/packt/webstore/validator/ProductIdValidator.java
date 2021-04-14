package com.packt.webstore.validator;

import com.packt.webstore.domain.Product;
import com.packt.webstore.exception.ProductNoFoundException;
import com.packt.webstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class ProductIdValidator implements ConstraintValidator<ProductId, String> {

    @Autowired
    private ProductService productService;

    @Override
    public void initialize(ProductId productId) {
        // celowo pozostawione puste; należy zainicjować adnotacę ograniczającą do sensownych domyślnych wartości
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Product product;
        try {
            product = productService.getProductById(value);
        } catch (ProductNoFoundException e) {
            return true;
        }
        if (product != null) {
            return false;
        }
        return true;
    }
}
