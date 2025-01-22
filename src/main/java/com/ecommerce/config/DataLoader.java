package com.ecommerce.config;

import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) {
        if (productRepository.count() == 0) {
            // Producto 1
            Product p1 = new Product();
            p1.setName("Laptop Dell XPS");
            p1.setDescription("Laptop de última generación");
            p1.setPrice(1299.99);
            p1.setImageUrl("https://picsum.photos/200/300");
            p1.setStock(10);
            productRepository.save(p1);

            // Producto 2
            Product p2 = new Product();
            p2.setName("iPhone 15");
            p2.setDescription("El último iPhone");
            p2.setPrice(999.99);
            p2.setImageUrl("https://picsum.photos/200/300");
            p2.setStock(15);
            productRepository.save(p2);

            // Producto 3
            Product p3 = new Product();
            p3.setName("Samsung TV 4K");
            p3.setDescription("Smart TV 55 pulgadas");
            p3.setPrice(699.99);
            p3.setImageUrl("https://picsum.photos/200/300");
            p3.setStock(5);
            productRepository.save(p3);
        }
    }
}