package com.digitalmenu.menuservice.category;

import com.digitalmenu.menuservice.dish.DishService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
class CategoryControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CategoryService categoryService;

    @MockBean
    DishService dishService;

    @Test
    void shouldReturnCategories() throws Exception {
        Category category1 = new Category(1, "Meat Lovers", "Meat Lovers");
        Category category2 = new Category(2, "Soups", "Soepen");
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category1);
        categoryList.add(category2);

        when(categoryService.getCategories()).thenReturn(categoryList);

        mockMvc.perform(get("/api/v1/categories"))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().json(convertObjectToJsonString(categoryList)));
    }

    @Test
    void shouldGetCategoryByName() throws Exception {
        String categoryName = "Meat Lovers";
        Category category = new Category(1, categoryName, categoryName);

        when(categoryService.getCategoryByName(categoryName)).thenReturn(java.util.Optional.of(category));

        mockMvc.perform(get("/api/v1/categories/{categoryName}", categoryName))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().json(convertObjectToJsonString(category)));
    }

    @Test
    void shouldCreateCategory() throws Exception {
        Category category = new Category(1, "Meat Lovers", "Meat Lovers");

        mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(category)))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void shouldUpdateCategory() throws Exception {
        int categoryId = 1;
        Category category = new Category(categoryId, "Deserts", "Toetjes");

        mockMvc.perform(put("/api/v1/categories/{categoryId}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(category)))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void shouldDeleteCategory() throws Exception {
        mockMvc.perform(delete("/api/v1/categories/{categoryId}", 1))
                .andDo(print()).andExpect(status().isOk());
    }

    private String convertObjectToJsonString(List<Category> categoryList) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(categoryList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private String convertObjectToJsonString(Category category) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(category);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}