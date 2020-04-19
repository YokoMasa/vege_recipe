package com.masalaboratory.vegetable.service;

import java.util.Optional;

import com.masalaboratory.vegetable.model.Image;
import com.masalaboratory.vegetable.model.Recipe;
import com.masalaboratory.vegetable.model.RecipeProc;
import com.masalaboratory.vegetable.repository.ImageRepository;
import com.masalaboratory.vegetable.repository.RecipeProcRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeProcServiceImpl implements RecipeProcService {

    @Autowired
    private RecipeProcRepository recipeProcRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public RecipeProc getById(int id) {
        Optional<RecipeProc> rp = recipeProcRepository.findById(id);
        if (!rp.isPresent()) {
            return null;
        }
        return rp.get();
    }

    @Override
    public RecipeProc create(RecipeProc recipeProc) {
        if (recipeProc.getImage() != null) {
            Image i = imageRepository.save(recipeProc.getImage());
            recipeProc.setImage(i);
        }
        return recipeProcRepository.saveAndFlush(recipeProc);
    }

    @Override
    public RecipeProc update(RecipeProc newRecipeProc) {
        if (newRecipeProc.getImage() != null) {
            imageRepository.save(newRecipeProc.getImage());
        }
        return recipeProcRepository.saveAndFlush(newRecipeProc);
    }

    @Override
    public RecipeProc delete(int id) {
        RecipeProc target = getById(id);
        if (target == null) {
            return null;
        }

        recipeProcRepository.delete(target);
        if (target.getImage() != null) {
            imageRepository.delete(target.getImage());
        }
        return target;
    }

}