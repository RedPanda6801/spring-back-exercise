package com.study.demo.dto;

import com.study.demo.entity.Recipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RecipeDto {
    private Integer id;

    private String description;

    private Integer amount;

    private String userid;

    private Integer productid;

    public RecipeDto(Recipe recipe){
        this.id = recipe.getId();
        this.description = recipe.getDescription();
        this.amount = recipe.getAmount();
        this.productid = recipe.getProduct().getId();
        this.userid = recipe.getUser().getUserid();
    }
}
