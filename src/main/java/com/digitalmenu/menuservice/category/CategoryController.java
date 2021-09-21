package com.digitalmenu.menuservice.category;

import org.springframework.beans.factory.annotation.Autowired;
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

    @DeleteMapping(path = "{categoryId}")
    public void DeleteCategory(@PathVariable("categoryId") Long categoryId) {
        categoryService.DeleteCategory(categoryId);
    }
}
