package com.soumo_codes.ecommerce.service;

import com.soumo_codes.ecommerce.model.Category;
import com.soumo_codes.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }
    public void addCategory(Category category){ // it will take category object as parameter
        categoryRepository.save(category);
    }

    public void removeCategoryByID(int id){
        categoryRepository.deleteById(id);
    }

    public Optional<Category> getCategoryById(int id){ // use Optional as it might be category or might not be
        return categoryRepository.findById(id);
    }
}
