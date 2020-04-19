package com.masalaboratory.vegetable.service;

import com.masalaboratory.vegetable.model.RecipeProc;

public interface RecipeProcService {

    public RecipeProc getById(int id);
    
    public RecipeProc create(RecipeProc recipeProc);

    public RecipeProc update(RecipeProc recipeProc);

    public void delete(int id);

}