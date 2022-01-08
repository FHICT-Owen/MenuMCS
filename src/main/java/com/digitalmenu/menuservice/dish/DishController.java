package com.digitalmenu.menuservice.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/dish")
public class DishController {

    private final DishService dishService;

    @Autowired
    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/{dishName}")
    public Optional<Dish> getDishByName(@PathVariable("dishName") String dishName) { return dishService.getDishByName(dishName);}

    @GetMapping
    public List<Dish> getDishes()
    {
        return dishService.getDishes();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('create:dishes')")
    public void addNewDish(@RequestBody Dish dish) {
        dishService.createDish(dish);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('delete:dishes')")
    public void deleteDish(@PathVariable Integer id) {
        dishService.removeDish(id);
    }

    @PutMapping(path = "/{dishId}")
    @PreAuthorize("hasAuthority('update:dishes')")
    public ResponseEntity<Dish> updateDish(@RequestBody Dish dish, @PathVariable("dishId") Integer dishId) {
        dishService.updateDish(dishId, dish);
        return new ResponseEntity<>(dish, HttpStatus.OK);
//        boolean success = dishService.updateDish(dishId, dish);
//        if (success)
//            return new ResponseEntity<>(dish, HttpStatus.OK);
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
