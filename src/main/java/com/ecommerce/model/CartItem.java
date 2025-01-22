package com.ecommerce.model;

public class CartItem {
    private Product product;
    private int quantity;

    // Constructor vacío
    public CartItem() {}

    // Getters y Setters
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}