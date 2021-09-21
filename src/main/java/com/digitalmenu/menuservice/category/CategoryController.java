package com.digitalmenu.menuservice.category;

import javassist.NotFoundException;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> GetCategories()
    {
        return categoryService.GetCategories();
    }

    @PutMapping(path = "/update/{categoryId}")
    public ResponseEntity<Category> UpdateCategory(@RequestBody Category category, @PathVariable("categoryId") Long categoryId) {
        boolean success = categoryService.UpdateCategory(categoryId, category);
        if (success)
            return new ResponseEntity<>(category, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "{categoryId}")
    public void DeleteCategory(@PathVariable("categoryId") Long categoryId) {
        categoryService.DeleteCategory(categoryId);
    }
}
