package com.masalaboratory.vegetable.controller;

import java.util.List;

import com.masalaboratory.vegetable.controller.form.RecipeForm;
import com.masalaboratory.vegetable.model.Image;
import com.masalaboratory.vegetable.model.Recipe;
import com.masalaboratory.vegetable.repository.RecipeRepository;
import com.masalaboratory.vegetable.service.ImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @Autowired
    private ImageService imageService;

    @GetMapping
    private String list(Model model) {
        List<Recipe> recipes = recipeRepository.findAll();
        model.addAttribute("test", "Recipe!!");
        model.addAttribute("recipes", recipes);
        return "recipe";
    }

    @PostMapping
    private String create(@Validated @ModelAttribute final RecipeForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("validation error!");
        }
        try {
            Image raw = imageService.save(form.getImage());
            Image thumbnail = imageService.createResizedImageFrom(raw, 300);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "recipe_created";
    }

}