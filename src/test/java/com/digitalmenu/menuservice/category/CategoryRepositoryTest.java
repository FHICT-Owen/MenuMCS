package com.digitalmenu.menuservice.category;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll(); // Clears database for every test
    }

    @Test
    void itShouldNotFindCategoryByName() {
        // given
        String name = "Fish";
        // when
        var expected = underTest.findCategoryByName(name);
        // then
        assertThat(expected).isEmpty();

    }

    @Test
    void itShouldFindCategoryByName() {
        // given
        Category category = new Category(
                "Meat"
        );
        underTest.save(category);
        // when
        var expected = underTest.findCategoryByName("Meat");
        // then
        assertThat(expected).isNotEmpty();

    }
}