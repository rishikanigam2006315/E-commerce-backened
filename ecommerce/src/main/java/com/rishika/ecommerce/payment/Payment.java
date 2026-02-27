package com.rishika.ecommerce.payment;

import com.rishika.ecommerce.order.Order;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String gatewayOrderId;
    private String gatewayPaymentId;
    private double amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private String gateway;
    private LocalDateTime createdAt;
    @OneToOne
    private Order order;
}

