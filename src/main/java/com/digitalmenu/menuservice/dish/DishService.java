package com.digitalmenu.menuservice.dish;

import com.digitalmenu.menuservice.exception.common.ElementAlreadyExistsException;
import com.digitalmenu.menuservice.exception.common.NoSuchElementFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DishService {

    private final DishRepository dishRepository;

    public List<Dish> getDishes() {
        return dishRepository.findAll();
    }

    public Dish createDish(Dish dish) {
        System.out.println("WJDJAWIDAWJODJAWO");

        if (dishRepository.existsDishByName(dish.getName()))
            throw new ElementAlreadyExistsException("Dish with name: " + dish.getName() + " already exists");
        System.out.println("YOOOOOOOOO");

        return dishRepository.save(dish);
    }

    public Dish updateDish(Dish dish) {
        var foundDish = dishRepository.findById(dish.getId())
                .orElseThrow(() -> new NoSuchElementFoundException("Dish not found"));

        foundDish.setName(dish.getName());
        foundDish.setName_NL(dish.getName_NL());
        foundDish.setDescription(dish.getDescription());
        foundDish.setDescription_NL(dish.getDescription_NL());
        foundDish.setCategory(dish.getCategory());
        foundDish.setDietaryRestrictions(dish.getDietaryRestrictions());
        foundDish.setIngredients(dish.getIngredients());
        foundDish.setPrize(dish.getPrize());
        foundDish.setImage(dish.getImage());

        return dishRepository.save(foundDish);
    }

    public void deleteDish(Integer id) {
        if (!dishRepository.existsById(id))
            throw new NoSuchElementFoundException("Dish not found");

        dishRepository.deleteById(id);
    }
}
