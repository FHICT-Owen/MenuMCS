package com.digitalmenu.menuservice.category;

import com.digitalmenu.menuservice.exception.ApiRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void deleteCategory(Integer categoryId) {
        boolean exists = categoryRepository.existsById(categoryId);
        if (!exists) {
            throw new ApiRequestException("Category with id " + categoryId + " does not exists");
        }
        else {
            categoryRepository.deleteById(categoryId);
        }
    }

    public void createCategory(Category category) {
        Optional<Category> categoryByName = categoryRepository.findCategoryByName(category.getName());
        if (categoryByName.isPresent()) {
            throw new EntityExistsException("Name already taken!");
        }
        categoryRepository.save(category);
    }

    public boolean updateCategory(Integer id, Category category) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Category actualCategory = optionalCategory.get();
            actualCategory.setName(category.getName());
            categoryRepository.save(actualCategory);
            return true;
        }
        return false;
    }

    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryByName(String name) {return categoryRepository.findCategoryByName(name);}

}
