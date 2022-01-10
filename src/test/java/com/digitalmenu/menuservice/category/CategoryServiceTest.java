package com.digitalmenu.menuservice.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityExistsException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository ;
    private CategoryService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CategoryService(categoryRepository);
    }

    @Test
    void canGetCategories() {
        // when
        underTest.getCategories();
        // then
        verify(categoryRepository).findAll();
    }

    @Test
    void canCreateCategory() {
        // given
        Category expected = new Category(
                1,
                "Meat",
                "vlees"
        );

        // when
        underTest.createCategory(expected);

        ArgumentCaptor<Category> dishArgumentCaptor =
                ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository)
                .save(dishArgumentCaptor.capture());

        Category actual = dishArgumentCaptor.getValue();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @Disabled
    void updateCategory() {
        // given
        Category category = new Category(
                1,
                "meat",
                "vlees"
        );
        categoryRepository.save(category);

        //when
        Category newCategory = new Category(
                1,
                "vegetables",
                "groente"
        );
        underTest.updateCategory(newCategory);

        // then
//        assertThat(underTest.getCategoryByName("vegetables")).isNotEqualTo(category);
    }

    @Test
    void willThrowWhenCategoryIsTaken() {
        // given
        Category category = new Category(
                1,
                "Meat",
                "vlees"
        );

        given(categoryRepository.findCategoryByName(anyString()))
                .willReturn(java.util.Optional.of(category));
        // when
        // then
        assertThatThrownBy(() -> underTest.createCategory(category))
                .isInstanceOf(EntityExistsException.class)
                .hasMessageContaining("Name already taken!");
        verify(categoryRepository, never()).save(any());
    }
}
