package com.masalaboratory.vegetable.service;

import java.util.Optional;

import com.masalaboratory.vegetable.model.Ingredient;
import com.masalaboratory.vegetable.model.Recipe;
import com.masalaboratory.vegetable.repository.IngredientRepository;
import com.masalaboratory.vegetable.repository.RecipeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Override
    public Ingredient create(int recipeId, Ingredient ingredient) {
        Optional<Recipe> r = recipeRepository.findById(recipeId);
        if (!r.isPresent()) {
            return null;
        }
        ingredient.setRecipe(r.get());
        return ingredientRepository.saveAndFlush(ingredient);
    }

    @Override
    public Ingredient update(Ingredient newIngredient) {
        Optional<Ingredient> i = ingredientRepository.findById(newIngredient.getId());
        if (!i.isPresent()) {
            return null;
        }
        Ingredient target = i.get();
        target.setName(newIngredient.getName());
        target.setQuantity(newIngredient.getQuantity());
        return ingredientRepository.saveAndFlush(target);
    }

    @Override
    public void delete(int id) {
        ingredientRepository.deleteById(id);
    }

}