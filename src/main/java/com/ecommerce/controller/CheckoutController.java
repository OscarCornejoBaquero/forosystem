package com.ecommerce.controller;

import com.ecommerce.model.Cart;
import com.ecommerce.model.Order;
import com.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private Cart cart;

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String showCheckoutForm(Model model) {
        if (cart.getItems().isEmpty()) {
            return "redirect:/cart";
        }
        model.addAttribute("cart", cart);
        return "checkout";
    }

    @PostMapping
    public String processCheckout(
            @RequestParam String customerName,
            @RequestParam String customerEmail,
            @RequestParam String shippingAddress,
            Model model) {
        
        try {
            Order order = orderService.createOrderFromCart(
                cart, 
                customerName, 
                customerEmail, 
                shippingAddress
            );
            
            cart.getItems().clear();
            
            return "redirect:/checkout/confirmation?orderId=" + order.getId();
        } catch (Exception e) {
            model.addAttribute("error", "Error procesando el pago: " + e.getMessage());
            model.addAttribute("cart", cart);
            return "checkout";
        }
    }

    @GetMapping("/confirmation")
    public String showConfirmation(@RequestParam Long orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "confirmation";
    }
}