package com.masalaboratory.vegetable.service;

import java.util.Optional;

import com.masalaboratory.vegetable.model.Recipe;
import com.masalaboratory.vegetable.repository.RecipeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipeService {

    private static final int PAGE_SIZE = 15;
    
    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public Page<Recipe> getPage(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE);
        return recipeRepository.findAll(pageable);
    }

    @Override
    public Recipe getById(int id) {
        Optional<Recipe> oRecipe = recipeRepository.findById(id);
        if (oRecipe.isPresent()) {
            return oRecipe.get();
        } else {
            return null;
        }
    }

    @Override
    public Recipe create(Recipe recipe) {
        return recipeRepository.saveAndFlush(recipe);
    }

    @Override
    public Recipe update(Recipe newRecipe) {
        return recipeRepository.saveAndFlush(newRecipe);
    }

    @Override
    public void delete(int id) {
        recipeRepository.deleteById(id);
    }

}