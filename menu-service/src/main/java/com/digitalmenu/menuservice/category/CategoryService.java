package com.digitalmenu.menuservice.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    public CategoryService()
    {
    }

    public void DeleteCategory(Long categoryId)
    {
        System.out.println("category " + categoryId + " deleted!");
    }
}
