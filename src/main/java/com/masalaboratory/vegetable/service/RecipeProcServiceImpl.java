package com.masalaboratory.vegetable.service;

import java.util.Optional;

import com.masalaboratory.vegetable.model.Image;
import com.masalaboratory.vegetable.model.Recipe;
import com.masalaboratory.vegetable.model.RecipeProc;
import com.masalaboratory.vegetable.repository.ImageRepository;
import com.masalaboratory.vegetable.repository.RecipeProcRepository;
import com.masalaboratory.vegetable.repository.RecipeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeProcServiceImpl implements RecipeProcService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeProcRepository recipeProcRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public RecipeProc create(int recipeId, RecipeProc recipeProc) {
        Optional<Recipe> r = recipeRepository.findById(recipeId);
        if (!r.isPresent()) {
            return null;
        }
        recipeProc.setRecipe(r.get());

        if (recipeProc.getImage() != null) {
            Image i = imageRepository.saveAndFlush(recipeProc.getImage());
            recipeProc.setImage(i);
        }
        return recipeProcRepository.saveAndFlush(recipeProc);
    }

    @Override
    public RecipeProc update(RecipeProc newRecipeProc) {
        Optional<RecipeProc> rp = recipeProcRepository.findById(newRecipeProc.getId());
        if (!rp.isPresent()) {
            return null;
        }

        RecipeProc target = rp.get();
        target.setDescription(newRecipeProc.getDescription());
        
        if (newRecipeProc.getImage() != null) {
            Image i = imageRepository.saveAndFlush(newRecipeProc.getImage());
            target.setImage(i);
        }
        return recipeProcRepository.saveAndFlush(target);
    }

    @Override
    public RecipeProc delete(int id) {
        Optional<RecipeProc> rp = recipeProcRepository.findById(id);
        if (!rp.isPresent()) {
            return null;
        }

        RecipeProc target = rp.get();
        recipeProcRepository.delete(target);

        if (target.getImage() != null) {
            imageRepository.delete(target.getImage());
        }
        return target;
    }

}