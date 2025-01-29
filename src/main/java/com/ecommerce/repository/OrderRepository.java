package com.ecommerce.repository;

import com.ecommerce.model.Order;
import com.ecommerce.model.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerEmail(String customerEmail);
    List<Order> findByStatus(OrderStatus status);
    List<Order> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Order> findByStatusAndCustomerEmail(OrderStatus status, String customerEmail);
    List<Order> findByTotalAmountGreaterThan(Double amount);
    Page<Order> findByStatus(OrderStatus status, Pageable pageable);
    
    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.status = :status")
    Double getTotalAmountByStatus(@Param("status") OrderStatus status);
    
    @Query("SELECT o FROM Order o ORDER BY o.createdAt DESC")
    List<Order> findLastOrders(Pageable pageable);
    
    @Query("SELECT DISTINCT o FROM Order o JOIN o.items i WHERE i.product.id = :productId")
    List<Order> findOrdersContainingProduct(@Param("productId") Long productId);
    
    @Query("SELECT AVG(o.totalAmount) FROM Order o WHERE o.customerEmail = :email")
    Double getAverageOrderAmountByCustomer(@Param("email") String email);
    
    @Query("SELECT COUNT(o) FROM Order o WHERE o.status = :status AND o.customerEmail = :email")
    Long countOrdersByStatusAndCustomer(@Param("status") OrderStatus status, @Param("email") String email);
    
    @Query("SELECT o.customerEmail, COUNT(o) as orderCount FROM Order o GROUP BY o.customerEmail HAVING COUNT(o) > :minOrders")
    List<Object[]> findFrequentCustomers(@Param("minOrders") Long minOrders);
}