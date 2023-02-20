package com.study.demo.service;

import com.study.demo.entity.Recipe;
import com.study.demo.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class RecipeService {

    @Autowired
    RecipeRepository recipeRepository;

    public void write(Recipe recipe){
        recipeRepository.save(recipe);
    }

    public List<Recipe> getAllRecipeByProductId(Integer id){
        return recipeRepository.findAllByProductId(id);
    }
}
