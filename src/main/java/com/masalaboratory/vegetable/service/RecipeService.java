package com.masalaboratory.vegetable.service;

import com.masalaboratory.vegetable.model.Recipe;

import org.springframework.data.domain.Page;

public interface RecipeService {
    
    public Page<Recipe> getPage(int page);

    public Recipe getById(int id);

}