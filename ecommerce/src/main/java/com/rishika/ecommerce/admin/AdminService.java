package com.rishika.ecommerce.admin;

import com.rishika.ecommerce.category.Category;
import com.rishika.ecommerce.category.CategoryRepository;
import com.rishika.ecommerce.order.Order;
import com.rishika.ecommerce.order.OrderRepository;
import com.rishika.ecommerce.order.OrderStatus;
import com.rishika.ecommerce.product.Product;
import com.rishika.ecommerce.product.ProductRepository;
import com.rishika.ecommerce.user.User;
import com.rishika.ecommerce.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public Category addCategory(Category category){
        return categoryRepository.save(category);
    }
    public void deleteCategory(Long id){
        categoryRepository.deleteById(id);
    }
    public Product updateProduct(Long id, Product updated){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(updated.getName());
        product.setPrice(updated.getPrice());
        product.setDescription(updated.getDescription());
        product.setImageUrl(updated.getImageUrl());
        product.setCategory(updated.getCategory());

        return productRepository.save(product);
    }
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }
    public Order updateOrderStatus(Long id, OrderStatus status){
        Order order = orderRepository.findById(id).orElseThrow();
        order.setStatus(status);
        return orderRepository.save(order);
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public void blockUser(Long id){
        User user = userRepository.findById(id).orElseThrow();
        user.setEnabled(false);
        userRepository.save(user);
    }
}
