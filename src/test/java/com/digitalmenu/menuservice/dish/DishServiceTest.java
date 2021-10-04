package com.digitalmenu.menuservice.dish;

import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityExistsException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DishServiceTest {

    @Mock
    private DishRepository dishRepository;
    private DishService underTest;

    @BeforeEach
    void setUp() {
        underTest = new DishService(dishRepository);
    }

    @Test
    void canGetDishes() {
        // when
        underTest.getDishes();
        // then
        verify(dishRepository).findAll();
    }

    @Test
    void canCreateDish() {
        // given
        Dish dish = new Dish(
                1,
                "Pumpkin soup",
                "Good Soup"
        );

        //when
        underTest.createDish(dish);

        // then
        ArgumentCaptor<Dish> dishArgumentCaptor =
                ArgumentCaptor.forClass(Dish.class);
        verify(dishRepository)
                .save(dishArgumentCaptor.capture());

        Dish capturedDish = dishArgumentCaptor.getValue();

        assertThat(capturedDish).isEqualTo(dish);
    }

    @Test
    void willThrowWhenDishIsTaken() {
        // given
        Dish dish = new Dish(
                1,
                "Pumpkin soup",
                "Good Soup"
        );

        given(dishRepository.findDishByName(anyString()))
                .willReturn(java.util.Optional.of(dish));
        // when
        // then
        assertThatThrownBy(() -> underTest.createDish(dish))
                .isInstanceOf(EntityExistsException.class)
                .hasMessageContaining("Name already taken!");
        verify(dishRepository, never()).save(any());
    }

    @Test
    @Disabled
    void removeDish() {
        // given
        Dish dish = new Dish(
                1,
                "Pumpkin soup",
                "Good Soup"
        );
        dishRepository.save(dish);

        //when
        // then
        underTest.removeDish(any());
        verify(dishRepository.deleteDishById(1));
    }

    @Test
    @Disabled
    void updateDish() {
    }
}