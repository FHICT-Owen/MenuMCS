package com.digitalmenu.menuservice.dish;

import org.springframework.beans.factory.annotation.Autowired;
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
        if (dishByName.get().getName().isEmpty()) {
            throw new EntityNotFoundException("Name is empty!");
        }
        if (dishByName.isPresent()) {
            throw new EntityExistsException("Name already taken!");
        }

        dishRepository.save(dish);
    }

    public void removeDish(Integer id) {
        boolean exists = dishRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("Dish with id " + id + " does not exist");
        }
        dishRepository.deleteDishById(id);
    }

    public boolean updateDish(Integer id, Dish dish) {
        Optional<Dish> oldDishById = dishRepository.findById(id);
        if (oldDishById.isPresent()) {
            Dish newDish = oldDishById.get();
            newDish.setName(dish.getName());
            newDish.setDescription(dish.getDescription());
            newDish.setCategory(dish.getCategory());
            newDish.setDietaryRestrictions(dish.getDietaryRestrictions());
            newDish.setIngredients(dish.getIngredients());
            newDish.setPrize(dish.getPrize());
            newDish.setImage(dish.getImage());
            dishRepository.save(newDish);
            return true;
        }
        else {
            throw new EntityExistsException("Dish does not exist with given id");
        }
    }

    public Optional<Dish> getDishByName(String name){
        return dishRepository.findDishByName(name);
    }
}
