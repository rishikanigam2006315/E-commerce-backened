package com.rishika.ecommerce.razorpay;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class RazorpayService {

    private final RazorpayClient razorpayClient;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    @Value("${app.dev-mode:false}")
    private boolean devMode;

    public String createOrder(int amount) throws RazorpayException {
//        if (devMode){
//            return """
//                    {
//                    "id": "order_dev_%d",
//                    "amount": %d,
//                    "currency" : "INR",
//                    "status": "created"
//                    }
//                    """.formatted(System.currentTimeMillis(), amount * 100);
//        }
        if (devMode) {
            return "{"
                    + "\"id\":\"order_dev_" + System.currentTimeMillis() + "\","
                    + "\"amount\":" + (amount * 100) + ","
                    + "\"currency\":\"INR\","
                    + "\"status\":\"created\""
                    + "}";
        }

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount * 100); // rupees → paise
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "txn_" + System.currentTimeMillis());

        Order order = razorpayClient.orders.create(orderRequest);
        return order.toString();
    }

    public boolean verifyPayment(String orderId, String paymentId, String signature) {
        if(devMode){
            return true;
        }
        try {
            String payload = orderId + "|" + paymentId;

            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey =
                    new SecretKeySpec(keySecret.getBytes(), "HmacSHA256");

            mac.init(secretKey);
            byte[] hash = mac.doFinal(payload.getBytes());

            String generatedSignature =
                    Base64.getEncoder().encodeToString(hash);

            return generatedSignature.equals(signature);

        } catch (Exception e) {
            return false;
        }
    }
}
