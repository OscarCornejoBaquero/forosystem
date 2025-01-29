package com.ecommerce.model;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class Cart {
    private List<CartItem> items = new ArrayList<>();

 
    public Cart() {}

 
    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

  
    public void addProduct(Product product) {
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        CartItem newItem = new CartItem();
        newItem.setProduct(product);
        newItem.setQuantity(1);
        items.add(newItem);
    }
    
    public double getTotal() {
        return items.stream()
            .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
            .sum();
    }
}