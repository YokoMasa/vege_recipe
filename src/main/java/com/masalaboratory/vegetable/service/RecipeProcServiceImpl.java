package com.masalaboratory.vegetable.service;

import java.util.Optional;

import com.masalaboratory.vegetable.model.RecipeProc;
import com.masalaboratory.vegetable.repository.RecipeProcRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeProcServiceImpl implements RecipeProcService {

    @Autowired
    private RecipeProcRepository recipeProcRepository;

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
        return recipeProcRepository.saveAndFlush(recipeProc);
    }

    @Override
    public RecipeProc update(RecipeProc newRecipeProc) {
        return recipeProcRepository.saveAndFlush(newRecipeProc);
    }

    @Override
    public void delete(int id) {
        recipeProcRepository.deleteById(id);
    }

}