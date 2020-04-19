package com.masalaboratory.vegetable.service;

import com.masalaboratory.vegetable.model.Ingredient;

public interface IngredientService {

    public Ingredient getById(int id);

    public Ingredient create(int recipeId, Ingredient ingredient);

    public Ingredient update(Ingredient ingredient);

    public void delete(int id);

}