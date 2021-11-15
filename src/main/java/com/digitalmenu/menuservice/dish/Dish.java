package com.digitalmenu.menuservice.dish;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
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

    public Dish() { }

    public Dish(String name) {
        this.name = name;
    }

    public Dish(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Dish(Integer id, String name, String description, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<String> getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    public void setDietaryRestrictions(Set<String> dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
    }

    public Set<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<String> ingredients) {
        this.ingredients = ingredients;
    }

    public Double getPrize() {
        return prize;
    }

    public void setPrize(Double prize) {
        this.prize = prize;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
