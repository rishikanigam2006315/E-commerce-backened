package com.rishika.ecommerce.order;

import com.rishika.ecommerce.product.Product;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Product product;
    private  int quantity;
    private double price;
}
