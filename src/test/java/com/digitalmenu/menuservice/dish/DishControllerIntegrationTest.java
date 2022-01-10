package com.digitalmenu.menuservice.dish;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(DishController.class)
public class DishControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    DishService dishService;

    @Test
    void shouldReturnDishes() throws Exception {
        Dish dish1 = new Dish(1, "soup");
        Dish dish2 = new Dish(2, "frikandel");
        List<Dish> dishList = new ArrayList<>();
        dishList.add(dish1);
        dishList.add(dish2);

        when(dishService.getDishes()).thenReturn(dishList);

        mockMvc.perform(get("/api/v1/dish"))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().json(convertObjectToJsonString(dishList)));
    }

    @Test
    @Disabled
    public void shouldGetDishByName() throws Exception {
        String dishName = "pizza";
        Dish dish = new Dish(dishName);

//        when(dishService.getDishByName(dishName)).thenReturn(java.util.Optional.of(dish));

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

    @Test
    void shouldUpdateDish() throws Exception {
        int dishId = 1;
        Dish dish = new Dish(dishId, "soup");

        mockMvc.perform(put("/api/v1/dish/{dishId}", dishId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(dish)))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void shouldDeleteDish() throws Exception {
        mockMvc.perform(delete("/api/v1/dish/{dishId}", 1))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    @Disabled
    public void shouldReturnExceptionMessageWhenDishNameNotFound() throws Exception {
        String name = "soup";

//        when(dishService.getDishByName(name)).thenThrow(new ApiRequestException("There are no dishes found with this name"));

        mockMvc.perform(get("/api/v1/dish/{dishName}", name))
                .andDo(print()).andExpect(status().is4xxClientError())
//                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ApiRequestException))
                .andExpect(result -> assertEquals("There are no dishes found with this name", result.getResolvedException().getMessage()));
    }

    private String convertObjectToJsonString(List<Dish> dishList) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(dishList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
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
