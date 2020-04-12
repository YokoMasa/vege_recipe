package com.masalaboratory.vegetable.controller;

import java.util.List;

import com.masalaboratory.vegetable.controller.form.RecipeForm;
import com.masalaboratory.vegetable.model.Ingredient;
import com.masalaboratory.vegetable.model.Recipe;
import com.masalaboratory.vegetable.model.RecipeProc;
import com.masalaboratory.vegetable.repository.RecipeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

    @Autowired
    private RecipeRepository recipeRepository;

    @GetMapping
    private String list(Model model) {
        List<Recipe> recipes = recipeRepository.findAll();
        model.addAttribute("test", "Recipe!!");
        model.addAttribute("recipes", recipes);
        
        Recipe r = recipes.get(0);
        System.out.println(r.toString());
        System.out.println("--------------ingredients---------------");
        for (Ingredient ingredient : r.getIngredients()) {
            System.out.println(ingredient.getName());
        }
        System.out.println("--------------procedure---------------");
        for (RecipeProc proc : r.getRecipeProcs()) {
            System.out.println(proc.getDescription());
        }

        return "recipe";
    }

    @PostMapping
    private String create(@Validated @ModelAttribute final RecipeForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("validation error!");
            for (final ObjectError objectError: bindingResult.getAllErrors()) {
                System.out.println(objectError.getDefaultMessage());
            }
        }
        System.out.println(form.toString());
        return "recipe_created";
    }

}