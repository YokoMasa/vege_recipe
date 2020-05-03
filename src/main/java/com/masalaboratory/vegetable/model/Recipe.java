package com.masalaboratory.vegetable.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Recipe")
public class Recipe implements Identifiable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "long_description")
    private String longDescription;

    @Column(name = "create_date")
    @Temporal(TemporalType.DATE)
    private Date createDate;

    @Column(name = "serving")
    private String serving;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<Ingredient> ingredients; 

    @Column(name = "ingredient_order")
    private String ingredientOrder;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<RecipeProc> recipeProcs;

    @Column(name = "recipe_proc_order")
    private String recipeProcOrder;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "header_image_id")
    private Image headerImage;

    @ManyToOne(cascade = CascadeType.ALL)
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

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<RecipeProc> getRecipeProcs() {
        return recipeProcs;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setRecipeProcs(List<RecipeProc> recipeProcs) {
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

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getServing() {
        return serving;
    }

    public void setServing(String serving) {
        this.serving = serving;
    }

    
}