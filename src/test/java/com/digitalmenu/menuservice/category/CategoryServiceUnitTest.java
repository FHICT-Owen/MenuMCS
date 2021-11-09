package com.digitalmenu.menuservice.category;
import com.digitalmenu.menuservice.exception.ApiRequestException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityExistsException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceUnitTest {

    @Mock
    private CategoryRepository categoryRepository ;

    @InjectMocks
    private CategoryService underTest;

    @Before
    public void setUp() {
        underTest = new CategoryService(categoryRepository);
    }

    @Test
    void CanDeleteCategory() {
        // given
        int categoryId = 42;

        given(categoryRepository.existsById(categoryId)).willReturn(true);
        // when
        // then
        underTest.deleteCategory(categoryId);
        verify(categoryRepository).deleteById(categoryId);
    }

    @Test
    void CantDeleteCategoryBecauseIdDoesNotExist()
    {
        // given
        int categoryId = 42;
        assertThatThrownBy(() -> underTest.deleteCategory(categoryId))
                .isInstanceOf(ApiRequestException.class)
                .hasMessageContaining("Category with id "+ categoryId +" does not exists");
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
                "Meat"
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
    void updateCategory() {
        // given
        Category category = new Category(
                1,
                "meat"
        );
        categoryRepository.save(category);

        //when
        Category newCategory = new Category(
                1,
                "vegetables"
        );
        underTest.updateCategory(1, newCategory);

        // then
        assertThat(underTest.getCategoryByName("vegetables")).isNotEqualTo(category);
    }

    @Test
    void willThrowWhenCategoryIsTaken() {
        // given
        Category category = new Category(
                1,
                "Meat"
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
