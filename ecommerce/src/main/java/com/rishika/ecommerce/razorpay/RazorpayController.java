package com.rishika.ecommerce.razorpay;

import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class RazorpayController {

    private final RazorpayService razorpayService;
    private final com.rishika.ecommerce.payment.PaymentService paymentService;

    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(@RequestParam int amount)
            throws RazorpayException {

        return ResponseEntity.ok(razorpayService.createOrder(amount));
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyPayment(
            @RequestBody PaymentVerifyRequest request
    ) {
        boolean isValid = razorpayService.verifyPayment(
                request.getOrderId(),
                request.getPaymentId(),
                request.getSignature()
        );

        if (!isValid) {
            paymentService.handlePaymentFailure(request.getOrderId());
            return ResponseEntity.badRequest().body("Payment verification failed");
        }

        paymentService.handlePaymentSuccess(
                request.getOrderId(),
                request.getPaymentId()
        );

        return ResponseEntity.ok("Payment verified successfully");
    }
}

