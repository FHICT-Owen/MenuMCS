package com.digitalmenu.menuservice.category;

import com.digitalmenu.menuservice.exception.common.ElementAlreadyExistsException;
import com.digitalmenu.menuservice.exception.common.NoSuchElementFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    public Category createCategory(Category category) {

        if (categoryRepository.existsCategoryByName(category.getName()))
            throw new ElementAlreadyExistsException("Category with name: " + category.getName() + " already exists");

        return categoryRepository.save(category);
    }

    public Category updateCategory(Category category) {
        var foundCategory = categoryRepository.findById(category.getId())
                .orElseThrow(() -> new NoSuchElementFoundException("Category not found"));
        foundCategory.setName(category.getName());
        foundCategory.setName_NL(category.getName_NL());
        return categoryRepository.save(foundCategory);
    }

    public void deleteCategory(Integer id) {
        if (!categoryRepository.existsById(id))
            throw new NoSuchElementFoundException("Category not found");

        categoryRepository.deleteById(id);
    }
}
