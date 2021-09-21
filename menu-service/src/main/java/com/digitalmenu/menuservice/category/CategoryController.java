package com.digitalmenu.menuservice.category;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/category")
public class CategoryController {


    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @DeleteMapping(path = "{categoryId}")
    public void DeleteCategory(@PathVariable("categoryId") Long categoryId) {
        categoryService.DeleteCategory(categoryId);
    }
}
