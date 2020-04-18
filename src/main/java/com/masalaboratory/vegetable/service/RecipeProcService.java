package com.masalaboratory.vegetable.service;

import com.masalaboratory.vegetable.model.RecipeProc;

public interface RecipeProcService {

    public RecipeProc create(int recipeId, RecipeProc recipeProc);

    public RecipeProc update(RecipeProc recipeProc);

    public RecipeProc delete(int id);

}