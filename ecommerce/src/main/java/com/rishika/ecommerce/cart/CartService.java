package com.rishika.ecommerce.cart;

import com.rishika.ecommerce.common.ApiResponse;
import com.rishika.ecommerce.product.Product;
import com.rishika.ecommerce.product.ProductRepository;
import com.rishika.ecommerce.user.User;
import com.rishika.ecommerce.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // TEMP: logged-in user (JWT ke baad replace karenge)
    private User getCurrentUser() {
        User user = new User();
        user.setId(1L); // test user
        return user;
    }

//    public void addToCart(Long productId, int quantity) {
//
//        User user = getCurrentUser();
//
//        Cart cart = cartRepository.findByUser(user)
//                .orElseGet(() -> {
//                    Cart newCart = new Cart();
//                    newCart.setUser(user);
//                    return cartRepository.save(newCart);
//                });
//
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//
//        CartItem item = new CartItem();
//        item.setCart(cart);
//        item.setProduct(product);
//        item.setQuantity(quantity);
//        item.setPrice(product.getPrice() * quantity);
//
//        cartItemRepository.save(item);
//    }

    public void addToCart(String email, Long productId, int quantity) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem item = new CartItem();
        item.setCart(cart);
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setPrice(product.getPrice() * quantity);

        cartItemRepository.save(item);
    }

    public List<CartItem> getCartItems() {
        Cart cart = cartRepository.findByUser(getCurrentUser())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        return cartItemRepository.findByCart_Id(cart.getId());
    }
    public Cart createCart(){
        Cart cart = new Cart();
        return cartRepository.save(cart);
    }

//    public List<CartItem> getCartItems(Long cartId) {
//        return cartItemRepository.findByCart_Id(cartId);
//    }

    public void removeFromCart(Long cartId, Long productId) {
        cartItemRepository.deleteByCart_IdAndProduct_Id(cartId, productId);
    }
}