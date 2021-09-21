package com.digitalmenu.menuservice.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {



    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void DeleteCategory(Long categoryId)
    {
        System.out.println("category " + categoryId + " deleted!");
    }

    public List<Category> GetCategories(){
        return categoryRepository.findAll();
    }
}
