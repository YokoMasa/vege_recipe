package com.masalaboratory.vegetable.controller;

import com.masalaboratory.vegetable.controller.form.RecipeProcForm;
import com.masalaboratory.vegetable.controller.helper.APIErrorHelper;
import com.masalaboratory.vegetable.controller.helper.ImageSaveHelper;
import com.masalaboratory.vegetable.model.Image;
import com.masalaboratory.vegetable.model.Recipe;
import com.masalaboratory.vegetable.model.RecipeProc;
import com.masalaboratory.vegetable.service.RecipeProcService;
import com.masalaboratory.vegetable.service.APIRecipeService;
import com.masalaboratory.vegetable.util.SavedImage;

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
@RequestMapping(path = "/api/recipe/{recipeId}/proc", produces = MediaType.APPLICATION_JSON_VALUE)
public class APIRecipeProcController extends ImageHandlingController {

    @Autowired
    private ImageSaveHelper imageSaveHelper;

    @Autowired
    private APIErrorHelper apiErrorHelper;

    @Autowired
    private APIRecipeService recipeService;

    @Autowired
    private RecipeProcService recipeProcService;

    @PostMapping
    public ResponseEntity<?> create(@PathVariable(name = "recipeId") int recipeId,
            @Validated @ModelAttribute RecipeProcForm form, BindingResult bindingResult) {
        Recipe recipe = recipeService.getById(recipeId);
        if (recipe == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiErrorHelper.getNotFoundError());
        }
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(apiErrorHelper.getValidationError(bindingResult));
        }

        RecipeProc proc = new RecipeProc();
        proc.setRecipe(recipe);
        proc.setDescription(form.getDescription());
        if (form.getImage() != null) {
            try {
                SavedImage raw = imageSaveHelper.save(form.getImage());
                SavedImage si = imageSaveHelper.createResizedImageFrom(raw, 230);
                Image i = toImage(si);
                proc.setImage(i);
                imageSaveHelper.delete(raw);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiErrorHelper.getInternalServerError());
            }
        }
        recipeProcService.create(proc);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @Validated @ModelAttribute RecipeProcForm form,
            BindingResult bindingResult) {
        RecipeProc proc = recipeProcService.getById(id);
        if (proc == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiErrorHelper.getNotFoundError());
        }
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(apiErrorHelper.getValidationError(bindingResult));
        }

        proc.setDescription(form.getDescription());

        if (form.getImage() != null) {
            try {
                SavedImage raw = imageSaveHelper.save(form.getImage());
                SavedImage si = imageSaveHelper.createResizedImageFrom(raw, 230);
                Image newImage = updateImage(proc.getImage(), si);
                proc.setImage(newImage);
                imageSaveHelper.delete(raw);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiErrorHelper.getInternalServerError());
            }
        }

        recipeProcService.update(proc);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        RecipeProc proc = recipeProcService.getById(id);
        if (proc == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiErrorHelper.getNotFoundError());
        }
        if (proc.getImage() != null) {
            try {
                imageSaveHelper.delete(toSavedImage(proc.getImage()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        recipeProcService.delete(id);
        return ResponseEntity.ok().build();
    }

}