package com.digitalmenu.menuservice.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.Optional;

@Service
public class DishService {

    private final DishRepository dishRepository;

    @Autowired
    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public void createDish(Dish dish) {
        Optional<Dish> dishByName = dishRepository.findDishByName(dish.getName());
        if (dishByName.isPresent()) {
            throw new EntityExistsException("Name already taken!");
        }
        dishRepository.save(dish);
    }
}
