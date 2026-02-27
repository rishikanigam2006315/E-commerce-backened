package com.rishika.ecommerce.payment;

import com.rishika.ecommerce.order.Order;
import com.rishika.ecommerce.order.OrderRepository;
import com.rishika.ecommerce.order.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public Payment createPayment(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setStatus(PaymentStatus.CREATED);
        payment.setGateway("RAZORPAY");
        payment.setCreatedAt(LocalDateTime.now());

        return paymentRepository.save(payment);
    }
    public void paymentSuccess(Long paymentId){
        Payment payment = paymentRepository.findById(paymentId).orElseThrow();
        payment.setStatus(PaymentStatus.SUCCESS);

        Order order = payment.getOrder();
        order.setStatus(OrderStatus.PAID);

        paymentRepository.save(payment);
        orderRepository.save(order);
    }
    public void handlePaymentSuccess(
            String razorpayOrderId,
            String razorpayPaymentId
    ){
        Payment payment = paymentRepository
                .findByGatewayOrderId(razorpayOrderId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setStatus(PaymentStatus.SUCCESS);
        paymentRepository.save(payment);

        Order order = payment.getOrder();
        order.setStatus(OrderStatus.PLACED);
        orderRepository.save(order);
    }
    public void handlePaymentFailure(String razorpayOrderId) {
        Payment payment = paymentRepository
                .findByGatewayOrderId(razorpayOrderId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setStatus(PaymentStatus.FAILED);
        paymentRepository.save(payment);
    }


}
