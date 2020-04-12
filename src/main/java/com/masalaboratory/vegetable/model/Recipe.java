package com.masalaboratory.vegetable.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Recipe")
public class Recipe {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "long_description")
    private String longDescription;

    @Column(name = "header_image_url")
    private String headerImageUrl;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @OneToMany(mappedBy = "recipe")
    private Set<Ingredient> ingredients; 

    @Column(name = "ingredient_order")
    private String ingredientOrder;

    @OneToMany(mappedBy = "recipe")
    private Set<RecipeProc> recipeProcs;

    @Column(name = "recipe_proc_order")
    private String recipeProcOrder;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getHeaderImageUrl() {
        return headerImageUrl;
    }

    public void setHeaderImageUrl(String headerImageUrl) {
        this.headerImageUrl = headerImageUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getIngredientOrder() {
        return ingredientOrder;
    }

    public void setIngredientOrder(String ingredientOrder) {
        this.ingredientOrder = ingredientOrder;
    }

    public String getRecipeProcOrder() {
        return recipeProcOrder;
    }

    public void setRecipeProcOrder(String recipeProcOrder) {
        this.recipeProcOrder = recipeProcOrder;
    }

}