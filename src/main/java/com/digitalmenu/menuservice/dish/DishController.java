package com.digitalmenu.menuservice.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PutMapping(path = "/update/{dishId}")
    public ResponseEntity<Dish> updateDish(@RequestBody Dish dish, @PathVariable("dishId") Integer dishId) {
        boolean success = dishService.updateDish(dishId, dish);
        if (success)
            return new ResponseEntity<>(dish, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
