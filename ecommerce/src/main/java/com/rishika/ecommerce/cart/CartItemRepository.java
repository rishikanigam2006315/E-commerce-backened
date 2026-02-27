package com.rishika.ecommerce.cart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCart_Id(Long cartId);

    void deleteByCart_IdAndProduct_Id(Long cartId, Long productId);
}
