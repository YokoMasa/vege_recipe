package com.masalaboratory.vegetable.controller;

import java.util.List;

import com.masalaboratory.vegetable.controller.form.RecipeForm;
import com.masalaboratory.vegetable.controller.helper.ImageSaveHelper;
import com.masalaboratory.vegetable.model.Image;
import com.masalaboratory.vegetable.model.Recipe;
import com.masalaboratory.vegetable.service.RecipeService;
import com.masalaboratory.vegetable.util.SavedImage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/recipe", produces = MediaType.APPLICATION_JSON_VALUE)
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private ImageSaveHelper imageSaveHelper;

    @GetMapping("/{id}")
    private ResponseEntity<Recipe> get(@PathVariable final int id) {
        final Recipe r =  recipeService.getById(id);
        if (r == null) {
            System.out.println("r == null!!");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(r);
    }

    @GetMapping
    private List<Recipe> list(@RequestParam(name = "page", defaultValue = "0") final int pageNum) {
        final Page<Recipe> page = recipeService.getPage(pageNum);
        return page.toList();
    }

    @PostMapping
    private ResponseEntity<?> create(@Validated @ModelAttribute final RecipeForm form, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("validation error!");
            return ResponseEntity.badRequest().body(bindingResult.getFieldErrors());
        }

        Image raw = null;
        Image thumbnail = null;
        if (form.getImage() != null) {
            try {
                final SavedImage siRaw = imageSaveHelper.save(form.getImage());
                raw = Image.from(siRaw);
                final SavedImage siThumbnail = imageSaveHelper.createResizedImageFrom(siRaw, 300);
                thumbnail = Image.from(siThumbnail);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
        final Recipe r = Recipe.from(form, raw, thumbnail);
        final Recipe created = recipeService.create(r);
        return ResponseEntity.ok().body(created);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@PathVariable final int id) {
        final Recipe r = recipeService.delete(id);
        try {
            if (r.getHeaderImage() != null) {
                imageSaveHelper.delete(r.getHeaderImage().toSavedImage());
            }
            if (r.getThumbnail() != null) {
                imageSaveHelper.delete(r.getThumbnail().toSavedImage());
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }

}