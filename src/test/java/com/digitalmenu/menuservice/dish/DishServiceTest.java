package com.digitalmenu.menuservice.dish;
import com.digitalmenu.menuservice.category.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
import static org.mockito.ArgumentMatchers.*;
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
    void shouldGetDishes() {
        // when
        underTest.getDishes();
        // then
        verify(dishRepository).findAll();
    }

    @Test
    void shouldCreateDish() {
        // given
        Dish expected = new Dish(
                1,
                "",
                "Good Soup",
                "soup"
        );
        // when
        underTest.createDish(expected);

        ArgumentCaptor<Dish> dishArgumentCaptor =
                ArgumentCaptor.forClass(Dish.class);
        verify(dishRepository)
                .save(dishArgumentCaptor.capture());

        Dish actual = dishArgumentCaptor.getValue();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldRemoveDish() {
        // given
        int dishId = 42;

        given(dishRepository.existsById(dishId)).willReturn(true);
        // when
        // then
        underTest.removeDish(dishId);
        verify(dishRepository).deleteById(dishId);
    }

    @Test
    void shouldUpdateCategory() {
        // given
        Dish expected = new Dish(
                1,
                "sou"
        );

        given(dishRepository.findById(expected.getId())).willReturn(Optional.of(expected));

        //when
        underTest.updateDish(expected.getId(), expected);
        ArgumentCaptor<Dish> dishArgumentCaptor =
                ArgumentCaptor.forClass(Dish.class);
        verify(dishRepository)
                .save(dishArgumentCaptor.capture());

        Dish actual = dishArgumentCaptor.getValue();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Disabled //Exception EntityNotFoundException has been removed service
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
}