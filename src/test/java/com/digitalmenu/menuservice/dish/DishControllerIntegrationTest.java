package com.digitalmenu.menuservice.dish;

import com.digitalmenu.menuservice.dish.DishService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
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


@WebMvcTest(DishController.class)
public class DishControllerIntegrationTest {
    @MockBean
    DishService dishService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldGetDishByName() throws Exception {
        String dishName = "pizza";
        Dish dish = new Dish(dishName);

        when(dishService.getDishByName(dishName)).thenReturn(java.util.Optional.of(dish));

        mockMvc.perform(get("/api/v1/dish/{dishName}", dishName))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().json(convertObjectToJsonString(dish)));
    }

    @Test
    public void shouldCreateDish() throws Exception {
        Dish dish = new Dish(1,"","Good Soup","soup");

        mockMvc.perform(post("/api/v1/dish/").contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(dish)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Disabled
    @Test
    void shouldUpdateDish() throws Exception {
        int dishId = 0;
        Dish dish = new Dish(dishId, "soup");

        mockMvc.perform(put("/api/v1/dish/{dishId}", dishId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonString(dish)))
            .andDo(print()).andExpect(status().isOk());
    }

    private String convertObjectToJsonString(Dish dish) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(dish);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
