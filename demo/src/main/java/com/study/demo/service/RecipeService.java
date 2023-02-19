package com.study.demo.service;

import com.study.demo.entity.Recipe;
import com.study.demo.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
public class RecipeService {

    @Autowired
    RecipeRepository recipeRepository;

    public void write(Recipe recipe){
        recipeRepository.save(recipe);
    }

    public Recipe getRecipeByProductId(Integer id){
        System.out.println(id);
        Recipe recipe = recipeRepository.findByProductId(id);
        System.out.println(recipe);
        return recipe;
    }
}
