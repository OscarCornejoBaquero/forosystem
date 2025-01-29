package com.ecommerce.service;

import com.ecommerce.model.*;
import com.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Transactional
    public Order createOrderFromCart(Cart cart, String customerName, String customerEmail, String shippingAddress) {
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setCustomerEmail(customerEmail);
        order.setShippingAddress(shippingAddress);
        
        double total = 0.0;
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            order.getItems().add(orderItem);
            
            total += orderItem.getSubtotal();
        }
        
        order.setTotalAmount(total);
        return orderRepository.save(order);
    }
    public List<Order> getLastFiveOrders() {
        return orderRepository.findLastOrders(PageRequest.of(0, 5));
    }
    
    public CustomerStats getCustomerStats(String email) {
        CustomerStats stats = new CustomerStats();
        stats.setTotalOrders(orderRepository.countOrdersByStatusAndCustomer(OrderStatus.DELIVERED, email));
        stats.setAverageOrderAmount(orderRepository.getAverageOrderAmountByCustomer(email));
        return stats;
    }
    
    public Double getDailySales() {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0);
        LocalDateTime endOfDay = LocalDateTime.now().withHour(23).withMinute(59);
        List<Order> dailyOrders = orderRepository.findByCreatedAtBetween(startOfDay, endOfDay);
        return dailyOrders.stream()
                .filter(order -> order.getStatus() == OrderStatus.PAID)
                .mapToDouble(Order::getTotalAmount)
                .sum();
    }
}
class CustomerStats {
    private Long totalOrders;
    private Double averageOrderAmount;
    
    public Long getTotalOrders() { return totalOrders; }
    public void setTotalOrders(Long totalOrders) { this.totalOrders = totalOrders; }
    public Double getAverageOrderAmount() { return averageOrderAmount; }
    public void setAverageOrderAmount(Double averageOrderAmount) { this.averageOrderAmount = averageOrderAmount; }
}