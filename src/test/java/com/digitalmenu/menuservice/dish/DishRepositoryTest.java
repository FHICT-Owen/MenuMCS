package com.digitalmenu.menuservice.dish;

import com.digitalmenu.menuservice.category.Category;
import com.digitalmenu.menuservice.dish.DishRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class DishRepositoryTest {

    @Autowired
    private DishRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll(); // Clears database for every test
    }

    @Test
    void itShouldDeleteDishById () {
        // given
        Dish dish = new Dish(
                1,
                "Pumpkin soup",
                "Good Soup"
        );
        underTest.save(dish);
        // when
        var expected = underTest.deleteDishById(1);
        // then
        assertThat(expected).isEqualTo(0);
    }

    @Test
    void itShouldFindDishByName() {
        // given
        Dish dish = new Dish(
                1,
                "Pumpkin soup",
                "Good Soup"
        );
        underTest.save(dish);
        String name = "Pumpkin soup";
        // when
        var expected = underTest.findDishByName(name);
        // then
        assertThat(expected).isNotEmpty();
    }

    @Test
    void itShouldFindAllDishes() {
        // given
        Dish dish_1 = new Dish(
                1,
                "Pumpkin soup",
                "Good Soup"
        );
        Dish dish_2 = new Dish(
                2,
                "French Fries",
                "Good Fries"
        );
        underTest.save(dish_1);
        underTest.save(dish_2);
        // when
        var expected = underTest.findAll();
        // then
        assertThat(expected).isNotNull();
    }

    @Test
    void itShouldFindNoDishes() {
        // given
        // when
        var expected = underTest.findAll();
        // then
        assertTrue(expected.isEmpty());
    }
}
