package com.digitalmenu.menuservice.dish;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/dish")
public class DishController {

    private final DishService dishService;

    @GetMapping
    public List<Dish> getCategories() {
        return dishService.getDishes();
    }

    @PostMapping
    public ResponseEntity<Dish> createDish(@RequestBody @Valid Dish dish) {
        return new ResponseEntity<>(dishService.createDish(dish), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Dish> updateDish(@RequestBody @Valid Dish dish) {
        return new ResponseEntity<>(dishService.updateDish(dish), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{DishId}")
    public void deleteDish(@PathVariable("DishId") Integer DishId) {
        dishService.deleteDish(DishId);
    }
}
