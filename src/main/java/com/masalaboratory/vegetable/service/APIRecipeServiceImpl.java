package com.masalaboratory.vegetable.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.masalaboratory.vegetable.model.Identifiable;
import com.masalaboratory.vegetable.model.Recipe;
import com.masalaboratory.vegetable.repository.RecipeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class APIRecipeServiceImpl extends BaseRecipeService implements APIRecipeService {

    private static final int PAGE_SIZE = 50;

    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public Page<Recipe> getPage(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE);
        return recipeRepository.findAllByOrderByCreateDateDesc(pageable);
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

    @Override
    public Recipe create(Recipe recipe) {
        recipe.setCreateDate(new Date());
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