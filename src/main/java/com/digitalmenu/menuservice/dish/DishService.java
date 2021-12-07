package com.digitalmenu.menuservice.dish;

import com.digitalmenu.menuservice.exception.ApiRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
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

    public void removeDish(Integer dishId) {
        if (!dishRepository.existsById(dishId)) {
            throw new ApiRequestException("Category with id " + dishId + " does not exists");
        }
        else {
            dishRepository.deleteById(dishId);
        }
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
        if (name.isEmpty())
        {
            throw new ApiRequestException("There are no dishes found with this name");
        }
        else {
            return dishRepository.findDishByName(name);
        }
    }
}
