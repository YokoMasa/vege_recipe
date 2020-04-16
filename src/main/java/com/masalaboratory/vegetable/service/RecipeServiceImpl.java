package com.masalaboratory.vegetable.service;

import com.masalaboratory.vegetable.model.Image;
import com.masalaboratory.vegetable.model.Recipe;
import com.masalaboratory.vegetable.repository.ImageRepository;
import com.masalaboratory.vegetable.repository.RecipeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipeService {

    private static final int PAGE_SIZE = 15;
    
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Page<Recipe> getPage(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE);
        return recipeRepository.findAll(pageable);
    }

    @Override
    public Recipe getById(int id) {
        return recipeRepository.getOne(id);
    }

    @Override
    public Recipe create(Recipe recipe) {
        if (recipe.getHeaderImage() != null) {
            Image header = imageRepository.saveAndFlush(recipe.getHeaderImage());
            recipe.setHeaderImage(header);
        }

        if (recipe.getThumbnail() != null) {
            Image thumbnail = imageRepository.saveAndFlush(recipe.getThumbnail());
            recipe.setThumbnail(thumbnail);
        }
        return recipeRepository.saveAndFlush(recipe);
    }

    @Override
    public Recipe update(Recipe recipe) {
        return recipeRepository.saveAndFlush(recipe);
    }

    @Override
    public Recipe delete(int id) {
        Recipe r = getById(id);
        recipeRepository.delete(r);
        if (r.getHeaderImage() != null) {
            imageRepository.delete(r.getHeaderImage());
        }
        if (r.getThumbnail() != null) {
            imageRepository.delete(r.getThumbnail());
        }
        return r;
    }

}