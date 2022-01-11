package com.digitalmenu.menuservice.category;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('access:restaurant')")
    public ResponseEntity<Category> createCategory(@RequestBody @Valid Category category) {
        return new ResponseEntity<>(categoryService.createCategory(category), HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('access:restaurant')")
    public ResponseEntity<Category> updateCategory(@RequestBody @Valid Category category) {
        return new ResponseEntity<>(categoryService.updateCategory(category), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{categoryId}")
    @PreAuthorize("hasAuthority('access:restaurant')")
    public void deleteCategory(@PathVariable("categoryId") Integer categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}
