package com.masalaboratory.vegetable.controller.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

public class RecipeForm {

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotBlank
    @Size(max = 511)
    private String shortDescription;

    @NotBlank
    @Size(max = 4095)
    private String longDescription;

    @Size(max = 255)
    private String ingredientOrder;

    @Size(max = 255)
    private String recipeProcOrder;

    private MultipartFile image;

    public String getName() {
        return name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public String getIngredientOrder() {
        return ingredientOrder;
    }

    public String getRecipeProcOrder() {
        return recipeProcOrder;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public void setIngredientOrder(String ingredientOrder) {
        this.ingredientOrder = ingredientOrder;
    }

    public void setRecipeProcOrder(String recipeProcOrder) {
        this.recipeProcOrder = recipeProcOrder;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "RecipeForm [image=" + image + ", ingredientOrder=" + ingredientOrder + ", longDescription="
                + longDescription + ", name=" + name + ", recipeProcOrder=" + recipeProcOrder + ", shortDescription="
                + shortDescription + "]";
    }

}