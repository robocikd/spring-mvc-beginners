package com.packt.webstore.exception;

public class ProductNoFoundException extends RuntimeException {
    public static final long serialVersionUID = 5522255055297128380L;
    private String productId;

    public ProductNoFoundException(String productId) {
        this.productId = productId;
    }

    public String getProductId(){
        return productId;
    }
}
