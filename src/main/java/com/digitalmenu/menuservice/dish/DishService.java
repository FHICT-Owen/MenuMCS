package com.digitalmenu.menuservice.dish;

import com.digitalmenu.menuservice.category.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
@Transactional
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

    public void removeDish(Integer id) {
        dishRepository.deleteDishById(id);
    }

    public boolean updateDish(Integer id, Dish dish) {
        Optional<Dish> optionalDish = dishRepository.findById(id);
        if (optionalDish.isPresent()) {
            Dish actualDish = optionalDish.get();
            actualDish.setName(dish.getName());
            actualDish.setDescription(dish.getDescription());
            actualDish.setImage(dish.getImage());
            dishRepository.save(actualDish);
            return true;
        }
        return false;
    }

    public Optional<Dish> getDishByName(String name){
        return dishRepository.findDishByName(name);
    }
}
