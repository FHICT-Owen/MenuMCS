package com.digitalmenu.menuservice.category;

import com.digitalmenu.menuservice.exception.ApiException;
import com.digitalmenu.menuservice.exception.ApiRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{categoryName}")
    public Optional<Category> getCategoryByName(@PathVariable("categoryName") String categoryName) { return categoryService.getCategoryByName(categoryName);}

    @GetMapping
    public List<Category> getCategories()
    {
        return categoryService.getCategories();
    }

    @PostMapping
    public void createCategory(@RequestBody Category category){
        try {
            categoryService.createCategory(category);
        }
        catch (Exception ex)
        {
            throw new ApiRequestException("Can't add category");
        }
    }

    @PutMapping(path = "/{categoryId}")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category, @PathVariable("categoryId") Integer categoryId) {
        categoryService.updateCategory(categoryId, category);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{categoryId}")
    public void deleteCategory(@PathVariable("categoryId") Integer categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}
