package com.digitalmenu.menuservice.category;
import com.digitalmenu.menuservice.exception.ApiRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @BeforeEach
    public void setUp() {
        underTest = new CategoryService(categoryRepository);
    }

    @Test
    void canDeleteCategoryOnDelete() {
        // given
        int categoryId = 42;

        given(categoryRepository.existsById(categoryId)).willReturn(true);
        // when
        // then
        underTest.deleteCategory(categoryId);
        verify(categoryRepository).deleteById(categoryId);
    }

    @Test
    void willThrownWhenIdDoesNotExistOnDelete()
    {
        // given
        int categoryId = 42;

        given(categoryRepository.existsById(categoryId)).willReturn(false);

        assertThatThrownBy(() -> underTest.deleteCategory(categoryId))
                .isInstanceOf(ApiRequestException.class)
                .hasMessageContaining("Category with id "+ categoryId +" does not exists");
    }

    @Test
    void canGetCategoriesOnGet() {
        // given
        given(categoryRepository.count()).willReturn(1L);
        // when
        underTest.getCategories();
        // then
        verify(categoryRepository).findAll();
    }

    @Test
    void throwsNoCategoriesFoundOnGet() {
        // given
        given(categoryRepository.count()).willReturn(0L);
        // when
        // then
        assertThatThrownBy(() -> underTest.getCategories())
                .isInstanceOf(ApiRequestException.class)
                .hasMessageContaining("There are no categories found");
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
    void willThrowWhenCategoryIsTakenOnCreate() {
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

    @Test
    void updateCategory() {
        // given
        Category expected = new Category(
                1,
                "meat"
        );

        given(categoryRepository.findById(expected.getId())).willReturn(Optional.of(expected));

        //when
        underTest.updateCategory(expected.getId(), expected);
        ArgumentCaptor<Category> dishArgumentCaptor =
                ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository)
                .save(dishArgumentCaptor.capture());

        Category actual = dishArgumentCaptor.getValue();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void willThrowWhenCategoryIsNotFoundOnUpdate() {
        // given
        int categoryId = 1;
        Category expected = new Category(
                categoryId,
                "Meat Lovers"
        );

        given(categoryRepository.findById(expected.getId())).willReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> underTest.updateCategory(expected.getId(), expected))
                .isInstanceOf(ApiRequestException.class)
                .hasMessageContaining("There is no category found with id " + categoryId);
    }

    @Test
    void canGetCategoryByNameOnGetCategory() {
        // given
        Category category = new Category(
                1,
                "Meat Lovers"
        );

        given(categoryRepository.findCategoryByName(category.getName())).willReturn(java.util.Optional.of(category));
        // when
        underTest.getCategoryByName(category.getName());
        // then
        verify(categoryRepository).findCategoryByName(category.getName());
    }

    @Test
    void willThrowWhenNameDoesNotExistOnGetCategory() {
        // given
        Category category = new Category(
                1,
                "Meat Lovers"
        );

        given(categoryRepository.findCategoryByName(category.getName())).willReturn(Optional.empty());
        // when
        // then
        assertThatThrownBy(() -> underTest.getCategoryByName(category.getName()))
                .isInstanceOf(ApiRequestException.class)
                .hasMessageContaining("There is no category found with name " + category.getName());
    }
}
