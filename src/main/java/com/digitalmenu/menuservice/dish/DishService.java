package com.digitalmenu.menuservice.dish;

import com.digitalmenu.menuservice.category.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class DishService {

    private final DishRepository dishRepository;

    @Autowired
    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public List<Dish> getDishes() {
        return dishRepository.findAll();
    }

    public void createDish(Dish dish) {
        Optional<Dish> dishByName = dishRepository.findDishByName(dish.getName());
        if (dishByName.isPresent()) {
            throw new EntityExistsException("Name already taken!");
        }
        dishRepository.save(dish);
    }

//    public void removeDish(Integer id){
//        Optional<Dish> dishById = dishRepository.findDishById(id);
//        if (!dishById.isPresent()) {
//            throw new EntityNotFoundException("Id not found!");
//        }
//        dishRepository.delete(dishRepository.findDishById(id).get());
//    }
}
