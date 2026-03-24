package com.rishika.ecommerce.admin;

import com.rishika.ecommerce.category.Category;
import com.rishika.ecommerce.image.ImageService;
import com.rishika.ecommerce.order.Order;
import com.rishika.ecommerce.order.OrderStatus;
import com.rishika.ecommerce.product.Product;
import com.rishika.ecommerce.product.ProductRepository;
import com.rishika.ecommerce.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;
    private final ImageService imageService;
    private final ProductRepository productRepository;

    @PostMapping("/category")
    public Category addCategory(@RequestBody Category category){
        return adminService.addCategory(category);
    }

    @DeleteMapping("/category/{id}")
    public void deleteCategory(@PathVariable Long id){
        adminService.deleteCategory(id);
    }


    @PutMapping("/product/{id}")
    public Product updateProduct(
            @PathVariable Long id,
            @RequestBody Product product
    ){
        return adminService.updateProduct(id, product);
    }

    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable Long id){
        adminService.deleteProduct(id);
    }


    @GetMapping("/orders")
    public List<Order> allOrders(){
        return adminService.getAllOrders();
    }

    @PutMapping("/order/{id}")
    public Order updateOrder(
            @PathVariable Long id,
            @RequestParam OrderStatus status
    ){
        return adminService.updateOrderStatus(id, status);
    }


    @GetMapping("/users")
    public List<User> users(){
        return adminService.getAllUsers();
    }

    @PutMapping("/block/{id}")
    public void blockUser(@PathVariable Long id){
        adminService.blockUser(id);
    }


    @PostMapping("/product/upload")
    public Product uploadProduct(
            @RequestPart("product") Product product,
            @RequestPart("image") MultipartFile image
    ) {
        String url = imageService.upload(image);
        product.setImageUrl(url);
        return productRepository.save(product);
    }
}