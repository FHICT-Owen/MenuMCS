package com.digitalmenu.menuservice.dish;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(
        name = "list-array",
        typeClass = ListArrayType.class
)
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
    @NotBlank
    @Column(nullable = false)
    private String name;
    @NotBlank
    @Column(nullable = false)
    private String name_NL;
    @NotBlank
    @Column(nullable = false)
    private String description;
    @NotBlank
    @Column(nullable = false)
    private String description_NL;
    @NotBlank
    @Column(nullable = false)
    private String category;
    @Type(type = "list-array")
    @Column(columnDefinition = "text[]")
    private List<String> dietaryRestrictions;
    @Type(type = "list-array")
    @Column(columnDefinition = "text[]")
    private List<String> ingredients;
    @Min(0)
    @Column(nullable = false)
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
