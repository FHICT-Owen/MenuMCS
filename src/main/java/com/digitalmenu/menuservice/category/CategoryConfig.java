package com.digitalmenu.menuservice.category;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CategoryConfig {

    @Bean
    CommandLineRunner commandLineRunner(CategoryRepository repository) {
        return args -> {
            Category meat = new Category(
                    1L,
                    "Meat"
            );
            Category vegetables = new Category(
                    1L,
                    "Vegetables"
            );
            repository.saveAll(
                    List.of(meat, vegetables)
            );
        };

    }

}
