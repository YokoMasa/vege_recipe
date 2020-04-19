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
public class RecipeProcController {

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
                Image i = Image.from(si);
                proc.setImage(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        RecipeProc created = recipeProcService.create(proc);
        if (created == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(created);
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
                if (proc.getImage() == null) {
                    Image i = new Image();
                    i.setSavePath(si.savePath);
                    i.setUrl(si.url);
                    proc.setImage(i);
                } else {
                    Image i = proc.getImage();
                    imageSaveHelper.delete(i.toSavedImage());
                    i.setSavePath(si.savePath);
                    i.setUrl(si.url);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        RecipeProc updated = recipeProcService.update(proc);
        if (updated == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        RecipeProc deleted = recipeProcService.delete(id);
        if (deleted.getImage() != null) {
            try {
                imageSaveHelper.delete(deleted.getImage().toSavedImage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.ok().build();
    }

}