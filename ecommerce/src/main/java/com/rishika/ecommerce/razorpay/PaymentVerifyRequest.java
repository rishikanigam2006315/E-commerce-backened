package com.rishika.ecommerce.razorpay;

import lombok.Data;

@Data
public class PaymentVerifyRequest {
    private String orderId;
    private String paymentId;
    private String signature;
}
