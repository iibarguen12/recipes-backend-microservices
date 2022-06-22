package com.abn.recipeservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String username;
    private String title;
    private String instructions;
    @ManyToMany(fetch = EAGER)
    private List<Ingredient> ingredients;
    @ManyToOne(fetch = EAGER)
    private FoodCategory foodCategory;
    private Integer servingDishes;
    @Column(insertable = false, updatable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date creationDate;
    private Time cookTime;
    private Time preparationTime;


}
