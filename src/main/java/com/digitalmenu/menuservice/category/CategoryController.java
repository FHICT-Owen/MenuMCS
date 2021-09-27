package com.digitalmenu.menuservice.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public List<Category> getCategories()
    {
        return categoryService.getCategories();
    }

    @PostMapping
    public void createCategory(@RequestBody Category category){
        categoryService.createCategory(category);
    }

    @PutMapping(path = "/update/{categoryId}")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category, @PathVariable("categoryId") Integer categoryId) {
        boolean success = categoryService.updateCategory(categoryId, category);
        if (success)
            return new ResponseEntity<>(category, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "{categoryId}")
    public void deleteCategory(@PathVariable("categoryId") Integer categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}
