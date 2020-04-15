package com.masalaboratory.vegetable.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @OneToMany(mappedBy = "recipe")
    private Set<Ingredient> ingredients; 

    @Column(name = "ingredient_order")
    private String ingredientOrder;

    @OneToMany(mappedBy = "recipe")
    private Set<RecipeProc> recipeProcs;

    @Column(name = "recipe_proc_order")
    private String recipeProcOrder;

    @ManyToOne
    @JoinColumn(name = "header_image_id")
    private Image headerImage;

    @ManyToOne
    @JoinColumn(name = "thumbnail_id")
    private Image thumbnail;

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

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public Set<RecipeProc> getRecipeProcs() {
        return recipeProcs;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setRecipeProcs(Set<RecipeProc> recipeProcs) {
        this.recipeProcs = recipeProcs;
    }

    public Image getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(Image headerImage) {
        this.headerImage = headerImage;
    }

    public Image getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Image thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "Recipe [headerImage=" + headerImage + ", id=" + id + ", ingredientOrder=" + ingredientOrder
                + ", ingredientSize=" + ingredients.size() + ", longDescription=" + longDescription + ", name=" + name
                + ", recipeProcOrder=" + recipeProcOrder + ", recipeProcSize=" + recipeProcs.size() + ", shortDescription="
                + shortDescription + ", thumbnail=" + thumbnail + "]";
    }

    
}