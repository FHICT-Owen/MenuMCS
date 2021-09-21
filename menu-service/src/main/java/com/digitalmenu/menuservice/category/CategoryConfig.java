package com.digitalmenu.menuservice.category;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryConfig {

    @Bean
    CommandLineRunner commandLineRunner(CategoryRepository repository) {
        return args -> new Category(
                1L,
                "Meat"
        );
    }

}
