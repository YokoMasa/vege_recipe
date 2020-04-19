package com.masalaboratory.vegetable.controller;

import java.util.List;

import com.masalaboratory.vegetable.controller.form.RecipeForm;
import com.masalaboratory.vegetable.controller.helper.APIErrorHelper;
import com.masalaboratory.vegetable.controller.helper.ImageSaveHelper;
import com.masalaboratory.vegetable.model.Image;
import com.masalaboratory.vegetable.model.Recipe;
import com.masalaboratory.vegetable.service.RecipeService;
import com.masalaboratory.vegetable.util.SavedImage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @Autowired
    private APIErrorHelper apiErrorHelper;

    @GetMapping("/{id}")
    private ResponseEntity<?> get(@PathVariable final int id) {
        final Recipe r =  recipeService.getById(id);
        if (r == null) {
            System.out.println("r == null!!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIError<>("resource not found"));
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
            return ResponseEntity.badRequest().body(apiErrorHelper.getValidationError(bindingResult));
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
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIError<>("could not save images"));
            }
        }
        final Recipe r = Recipe.from(form, raw, thumbnail);
        final Recipe created = recipeService.create(r);
        return ResponseEntity.ok().body(created);
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> update(@PathVariable(name = "id") int id, @Validated @ModelAttribute RecipeForm form, BindingResult bindingResult) {
        Recipe target = recipeService.getById(id);
        if (target == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (bindingResult.hasErrors()) {
            System.out.println("validation error!");
            return ResponseEntity.badRequest().body(apiErrorHelper.getValidationError(bindingResult));
        }

        target.setName(form.getName());
        target.setShortDescription(form.getShortDescription());
        target.setLongDescription(form.getLongDescription());
        target.setRecipeProcOrder(form.getRecipeProcOrder());
        target.setIngredientOrder(form.getIngredientOrder());
        if (form.getImage() != null) {
            try {
                final SavedImage siHeader = imageSaveHelper.save(form.getImage());
                if (target.getHeaderImage() != null) {
                    Image i = target.getHeaderImage();
                    imageSaveHelper.delete(i.toSavedImage());
                    i.setSavePath(siHeader.savePath);
                    i.setUrl(siHeader.url);
                } else {
                    Image i = new Image();
                    i.setSavePath(siHeader.savePath);
                    i.setUrl(siHeader.url);
                    target.setHeaderImage(i);
                }

                final SavedImage siThumbnail = imageSaveHelper.createResizedImageFrom(siHeader, 300);
                if (target.getThumbnail() != null) {
                    Image i = target.getThumbnail();
                    imageSaveHelper.delete(i.toSavedImage());
                    i.setSavePath(siThumbnail.savePath);
                    i.setUrl(siThumbnail.url);
                } else {
                    Image i = new Image();
                    i.setSavePath(siThumbnail.savePath);
                    i.setUrl(siThumbnail.url);
                    target.setThumbnail(i);
                }
            } catch (final Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIError<>("could not save images"));
            }
        }
        recipeService.update(target);
        return ResponseEntity.ok().build();
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