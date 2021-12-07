package com.digitalmenu.menuservice.dish;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dish {
    @Id
    @SequenceGenerator(
            name = "dish_sequence",
            sequenceName = "dish_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "dish_sequence"
    )
    private Integer id;
    private String name;
    private String description;
    private String category;
    @ElementCollection
    private Set<String> dietaryRestrictions;
    @ElementCollection
    private Set<String> ingredients;
    private Double prize;
    @Type(type = "org.hibernate.type.TextType")
    private String image;

    public Dish(Integer id, String name, String description, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public Dish(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Dish(String name) {
        this.name = name;
    }
}
