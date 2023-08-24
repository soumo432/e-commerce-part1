package com.soumo_codes.ecommerce.repository;

import com.soumo_codes.ecommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
