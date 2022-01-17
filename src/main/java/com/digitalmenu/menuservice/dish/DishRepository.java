package com.digitalmenu.menuservice.dish;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<Dish, Integer> {
    Optional<Dish> findDishByName(String name);
    void deleteDishById(Integer id);
    boolean existsDishByName(String name);
}
