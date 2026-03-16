package com.rishika.ecommerce.product;

import com.rishika.ecommerce.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // ADMIN
    @PostMapping("/add/{categoryId}")
    public Product addProduct(
            @RequestBody Product product,
            @PathVariable Long categoryId
    ) {
        return productService.addProduct(product, categoryId);
    }

    @GetMapping("/category/{id}")
    public List<Product> getByCategory(@PathVariable Long id) {
        return productService.getProductsByCategory(id);
    }

    @GetMapping
    public ApiResponse<List<Product>> getAllProducts(){
        return new ApiResponse<>(
                true,
                "Products fetched successfully",
                productService.getAllProducts()
        );
    }
    @GetMapping("/paged")
    public Page<Product> getPagedProducts(
            @RequestParam int page,
            @RequestParam int size
    ){
        return productService.getProducts(page, size);
    }

    @GetMapping("/search")
    public List<Product> search(@RequestParam String keyword) {
        return productService.search(keyword);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return new ApiResponse<>(
                true,
                "Product deleted successfully",
                null
        );
    }
}
