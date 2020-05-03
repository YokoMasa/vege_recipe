package com.masalaboratory.vegetable.controller;

import com.masalaboratory.vegetable.controller.form.IngredientForm;
import com.masalaboratory.vegetable.controller.helper.APIErrorHelper;
import com.masalaboratory.vegetable.controller.helper.APIErrorHelper.APIFieldError;
import com.masalaboratory.vegetable.model.Ingredient;
import com.masalaboratory.vegetable.service.IngredientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping(path = "/api/recipe/{recipeId}/ingredient", produces = MediaType.APPLICATION_JSON_VALUE)
public class APIIngredientController {

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private APIErrorHelper apiErrorHelper;

    @PostMapping
    public ResponseEntity<?> create(@PathVariable(name = "recipeId") int recipeId, @Validated @ModelAttribute IngredientForm form, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(apiErrorHelper.getValidationError(bindingResult));
        }
        Ingredient i = new Ingredient();
        i.setName(form.getName());
        i.setQuantity(form.getQuantity());
        
        ingredientService.create(recipeId, i);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @Validated @ModelAttribute IngredientForm form, BindingResult bindingResult) {
        Ingredient i = ingredientService.getById(id);
        if (i == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiErrorHelper.getNotFoundError());
        }
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(apiErrorHelper.getValidationError(bindingResult));
        }
        i.setName(form.getName());
        i.setQuantity(form.getQuantity());
        ingredientService.update(i);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        Ingredient i = ingredientService.getById(id);
        if (i == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiErrorHelper.getNotFoundError());
        }
        ingredientService.delete(id);
        return ResponseEntity.ok().build();
    }
}