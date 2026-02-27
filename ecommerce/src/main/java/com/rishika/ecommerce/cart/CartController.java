package com.rishika.ecommerce.cart;

import com.rishika.ecommerce.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // ➕ ADD TO CART
    @PostMapping("/add")
    public ApiResponse<?> addToCart(@RequestBody CartRequest request) {

        cartService.addToCart(
                //request.getCartId(),
                request.getProductId(),
                request.getQuantity()
        );

        return new ApiResponse<>(true, "Item added to cart", null);
    }

    @PostMapping("/create")
    public ApiResponse<Cart> createCart() {
        Cart cart = cartService.createCart();
        return new ApiResponse<>(true, "Cart created", cart);
    }

    // 📦 GET CART ITEMS
    @GetMapping
    public ApiResponse<List<CartItem>> getCart(
            @RequestParam Long cartId
    ) {
        return new ApiResponse<>(
                true,
                "Cart fetched successfully",
                cartService.getCartItems()
        );
    }

    @DeleteMapping("/remove")
    public ApiResponse<?> removeItem(
            @RequestParam Long cartId,
            @RequestParam Long productId
    ) {
        cartService.removeFromCart(cartId, productId);
        return new ApiResponse<>(true, "Item removed from cart", null);
    }
}