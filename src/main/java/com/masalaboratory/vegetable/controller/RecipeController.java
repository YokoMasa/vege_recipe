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
@RequestMapping("/recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private ImageSaveHelper imageSaveHelper;

    @GetMapping("/{id}")
    private Recipe get(@PathVariable int id) {
        return recipeService.getById(id);
    }

    @GetMapping
    private List<Recipe> list(@RequestParam(name = "page", defaultValue = "0") int pageNum) {
        Page<Recipe> page = recipeService.getPage(pageNum);
        return page.toList();
    }

    @PostMapping
    private String create(@Validated @ModelAttribute final RecipeForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("validation error!");
            return "validation error!";
        }

        Image raw = null;
        Image thumbnail = null;
        if (form.getImage() != null) {
            try {
                SavedImage siRaw = imageSaveHelper.save(form.getImage());
                raw = Image.from(siRaw);
                SavedImage siThumbnail = imageSaveHelper.createResizedImageFrom(siRaw, 300);
                thumbnail = Image.from(siThumbnail);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Recipe r = Recipe.from(form, raw, thumbnail);
        recipeService.create(r);
        return "recipe_created";
    }

    @DeleteMapping("/{id}")
    private String delete(@PathVariable int id) {
        Recipe r = recipeService.delete(id);
        try {
            if (r.getHeaderImage() != null) {
                imageSaveHelper.delete(r.getHeaderImage().toSavedImage());
            }
            if (r.getThumbnail() != null) {
                imageSaveHelper.delete(r.getThumbnail().toSavedImage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "deleted";
    }

}