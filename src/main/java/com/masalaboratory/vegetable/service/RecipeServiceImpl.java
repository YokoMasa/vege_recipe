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
public class RecipeServiceImpl extends BaseRecipeService implements RecipeService {

    private static final int PAGE_SIZE = 6;

    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public Page<Recipe> getPage(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return recipeRepository.findAllByStatusOrderByCreateDateDesc(Recipe.STATUS_PUBLIC, pageable);
    }

    @Override
    public Recipe getById(int id) {
        Optional<Recipe> oRecipe = recipeRepository.findById(id);
        if (oRecipe.isPresent()) {
            Recipe r = oRecipe.get();
            sortField(r);
            return r;
        } else {
            return null;
        }
    }
    
}