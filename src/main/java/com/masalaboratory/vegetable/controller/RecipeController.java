package com.masalaboratory.vegetable.controller;

import javax.websocket.server.PathParam;

import com.fasterxml.jackson.core.sym.Name;
import com.masalaboratory.vegetable.model.Recipe;
import com.masalaboratory.vegetable.service.RecipeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping
    public String list(@RequestParam(name = "page", defaultValue = "0") int pageNum, Model model) {
        Page<Recipe> page = recipeService.getPage(pageNum);
        model.addAttribute("page", page);
        return "recipe_list";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable(name = "id") int id, Model model) {
        Recipe r = recipeService.getById(id);
        model.addAttribute("recipe", r);
        return "recipe";
    }

}