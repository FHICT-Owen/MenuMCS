package com.digitalmenu.menuservice.dish;

import com.digitalmenu.menuservice.category.Category;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
                5,
                "Pumpkin soup",
                "Good Soup",
                "soup"
        );

        // when
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
    void willThrowWhenDishIsEmptyOnCreate() {
        // given
        Dish dish = new Dish(
                1,
                "",
                "Good Soup",
                "soup"
        );

        // when
        given(dishRepository.findDishByName(""))
                .willReturn(java.util.Optional.of(dish));

        // then
        assertThatThrownBy(() -> underTest.createDish(dish))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Name is empty!");
        verify(dishRepository, never()).save(any());
    }

    @Test
    void willThrowWhenDishIsEmptyOnUpdate() {
        // given
        Dish dish = new Dish(
                1,
                "Pumpkin Soup",
                "Good Soup",
                "soup"
        );

        // when
        given(dishRepository.findById(dish.getId()))
                .willReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> underTest.updateDish(dish.getId(), dish))
                .isInstanceOf(EntityExistsException.class)
                .hasMessageContaining("Dish does not exist with given id");
        verify(dishRepository, never()).save(any());
    }

    @Test
    void willThrowWhenDishIsTakenOnCreate() {
        // given
        Dish dish = new Dish(
                1,
                "Pumpkin Soup",
                "Good Soup",
                "soup"
        );

        // when
        given(dishRepository.findDishByName("Pumpkin Soup"))
                .willReturn(java.util.Optional.of(dish));
        // then
        assertThatThrownBy(() -> underTest.createDish(dish))
                .isInstanceOf(EntityExistsException.class)
                .hasMessageContaining("Name already taken!");
        verify(dishRepository, never()).save(any());
    }

    @Test
    void removeDish() {
        // given
        int dishId = 42;

        given(dishRepository.existsById(dishId)).willReturn(true);
        // when
        // then
        underTest.removeDish(dishId);
        verify(dishRepository).deleteById(dishId);
    }

    @Test
    void updateDish() {
        // given
        Dish oldDish = new Dish(
                1,
                "Pumpkin-soup",
                "Good Soup",
                "soup"
        );
        Dish newDish = new Dish(
                1,
                "Cheese-soup",
                "Good Soup",
                "soup"
        );

        given(dishRepository.findById(oldDish.getId()))
                .willReturn(java.util.Optional.of(oldDish));
        //when
        underTest.updateDish(oldDish.getId(), newDish);

        // then
        assertThat(underTest.getDishByName(newDish.getName())).isNotEqualTo(oldDish);
    }
}