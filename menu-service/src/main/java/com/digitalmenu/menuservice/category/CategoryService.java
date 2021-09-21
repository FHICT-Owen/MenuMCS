package com.digitalmenu.menuservice.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void DeleteCategory(Long categoryId)
    {
        System.out.println("category " + categoryId + " deleted!");
    }




    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }
}
