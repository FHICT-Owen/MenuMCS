package com.digitalmenu.menuservice.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("api/v1/dish")
public class DishController {

    private final DishService dishService;

    @Autowired
    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    public List<Dish> getCategories()
    {
        return dishService.getDishes();
    }

    @PostMapping
    public void addNewDish(@RequestBody Dish dish) {
        dishService.createDish(dish);
    }

    @DeleteMapping("/{id}")
    public void deleteDish(@PathVariable Integer id) {
        dishService.removeDish(id);
    }
}
