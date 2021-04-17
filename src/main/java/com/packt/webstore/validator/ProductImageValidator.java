package com.packt.webstore.validator;

import com.packt.webstore.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.io.ObjectStreamClass;
import java.util.Locale;

@Component
public class ProductImageValidator implements Validator {

    long allowedSize;

    @Override
    public boolean supports(Class<?> aClass) {
        return Product.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Product product = (Product) target;
        if (product.getProductImage() != null && product.getProductImage().getSize() > allowedSize) {

            errors.rejectValue("productImage", "com.packt.webstore.validator.ProductImageValidator.message");
        }
    }

    public long getAllowedSize() {
        return allowedSize;
    }

    public void setAllowedSize(long allowedSize) {
        this.allowedSize = allowedSize;
    }
}
