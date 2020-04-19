package com.masalaboratory.vegetable.controller;

import com.masalaboratory.vegetable.controller.form.RecipeProcForm;
import com.masalaboratory.vegetable.controller.helper.ImageSaveHelper;
import com.masalaboratory.vegetable.model.Image;
import com.masalaboratory.vegetable.model.Recipe;
import com.masalaboratory.vegetable.model.RecipeProc;
import com.masalaboratory.vegetable.service.RecipeProcService;
import com.masalaboratory.vegetable.service.RecipeService;
import com.masalaboratory.vegetable.util.SavedImage;

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
@RequestMapping(path = "/recipe/{recipeId}/proc", produces = MediaType.APPLICATION_JSON_VALUE)
public class RecipeProcController extends ImageHandlingController {

    @Autowired
    private ImageSaveHelper imageSaveHelper;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeProcService recipeProcService;

    @PostMapping
    public ResponseEntity<?> create(@PathVariable(name = "recipeId") int recipeId,
            @Validated @ModelAttribute RecipeProcForm form, BindingResult bindingResult) {
        Recipe recipe = recipeService.getById(recipeId);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().build();
        }

        RecipeProc proc = new RecipeProc();
        proc.setRecipe(recipe);
        proc.setDescription(form.getDescription());
        if (form.getImage() != null) {
            try {
                SavedImage si = imageSaveHelper.save(form.getImage());
                Image i = toImage(si);
                proc.setImage(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        recipeProcService.create(proc);
        return ResponseEntity.ok().body(proc);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @Validated @ModelAttribute RecipeProcForm form,
            BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().build();
        }

        RecipeProc proc = recipeProcService.getById(id);
        proc.setDescription(form.getDescription());

        if (form.getImage() != null) {
            try {
                SavedImage si = imageSaveHelper.save(form.getImage());
                Image newImage = updateImage(proc.getImage(), si);
                proc.setImage(newImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        recipeProcService.update(proc);
        return ResponseEntity.ok().body(proc);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        
        RecipeProc proc = recipeProcService.getById(id);
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