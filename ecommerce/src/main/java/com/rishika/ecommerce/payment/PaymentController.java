package com.rishika.ecommerce.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/create/{orderId}")
    public Payment create(@PathVariable Long orderId) {
        return paymentService.createPayment(orderId);
    }

    @PostMapping("/success/{paymentId}")
    public String success(@PathVariable Long paymentId) {
        paymentService.paymentSuccess(paymentId);
        return "Payment successful & Order marked PAID";
    }


}
