package com.packt.webstore.exception;

public class InvalidCartException extends RuntimeException {
    public static final long serialVersionUID = -509742384794804264L;
    private String cartId;
    public InvalidCartException(String cartId){
        this.cartId = cartId;
    }

    public String getCartId() {
        return cartId;
    }
}
