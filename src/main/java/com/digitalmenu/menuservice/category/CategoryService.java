package com.digitalmenu.menuservice.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void deleteCategory(Long categoryId)
    {
        boolean exists = categoryRepository.existsById(categoryId);
        if (!exists) {
            throw new IllegalStateException("Category with id " + categoryId + " does not exists");
        }
        else {
            categoryRepository.deleteById(categoryId);
            System.out.println("category " + categoryId + " deleted!");
        }
    }

  /*  public void UpdateCategory(long id, String name) {
        Category myCategory = categoryRepository.findById(id);
        myCategory.name = name;
        categoryRepository.save(myCategory);
    }*/

    public boolean updateCategory(Long id, Category category) {
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
}
