package com.ecommerce.controller;

import com.ecommerce.model.Cart;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {
    
    @Autowired
    private Cart cart;
    
    @Autowired
    private ProductRepository productRepository;
    
    @GetMapping
    public String showCart(Model model) {
        model.addAttribute("cart", cart);
        return "cart";
    }
    
    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            cart.addProduct(product);
        }
        return "redirect:/cart";
    }
}