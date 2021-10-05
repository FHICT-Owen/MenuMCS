package com.digitalmenu.menuservice.category;

import com.digitalmenu.menuservice.dish.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findById(int id);
    Optional<Category> findCategoryByName(String name);
}
