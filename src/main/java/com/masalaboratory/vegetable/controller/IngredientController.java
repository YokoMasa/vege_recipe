package com.masalaboratory.vegetable.controller;

import com.masalaboratory.vegetable.controller.form.IngredientForm;
import com.masalaboratory.vegetable.model.Ingredient;
import com.masalaboratory.vegetable.service.IngredientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/recipe/{recipeId}/ingredient", produces = MediaType.APPLICATION_JSON_VALUE)
public class IngredientController {

    @Autowired
    IngredientService ingredientService;

    @PostMapping
    public ResponseEntity<?> create(@PathVariable(name = "recipeId") int recipeId, @Validated @ModelAttribute IngredientForm form, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldErrors());
        }
        Ingredient i = new Ingredient();
        i.setName(form.getName());
        i.setQuantity(form.getQuantity());
        
        Ingredient created = ingredientService.create(recipeId, i);
        if (created == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @Validated @ModelAttribute IngredientForm form, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldErrors());
        }
        Ingredient i = new Ingredient();
        i.setId(id);
        i.setName(form.getName());
        i.setQuantity(form.getQuantity());

        Ingredient updated = ingredientService.update(i);
        if (updated == null) {
            return ResponseEntity.badRequest().body("updated == null");
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        ingredientService.delete(id);
        return ResponseEntity.ok().build();
    }
}