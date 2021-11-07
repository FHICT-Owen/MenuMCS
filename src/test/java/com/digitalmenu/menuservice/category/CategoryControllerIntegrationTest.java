package com.digitalmenu.menuservice.category;

import com.digitalmenu.menuservice.dish.DishService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class CategoryControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CategoryService categoryService;

    @MockBean
    DishService dishService;

    @MockBean
    CategoryRepository categoryRepository;

    List<Category> categories = new ArrayList<>();

    @BeforeEach
    public void setUp()
    {
        Category category1 = new Category(
                1,
                "meat"
        );
        Category category2 = new Category(
                2,
                "fish"
        );
        categories.add(category1);
        categories.add(category2);
    }

    @Test
    void shouldGetCategories() throws Exception {
        when(categoryService.getCategories())
                .thenReturn(categories);
        mockMvc.perform(get("/api/v1/categories"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        verify(categoryService).getCategories();
    }
}