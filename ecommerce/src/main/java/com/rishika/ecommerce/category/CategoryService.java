package com.rishika.ecommerce.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category addCategory(Category category){
        return categoryRepository.save(category);
    }
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }
    public Category getCategoryById(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }
    public Category updateCategory(Long id, Category newCategory){
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        existing.setName(newCategory.getName());
        existing.setDesciption(newCategory.getDesciption());
        return categoryRepository.save(existing);
    }
    public void deleteCategory(Long id){
        categoryRepository.deleteById(id);
    }
}
