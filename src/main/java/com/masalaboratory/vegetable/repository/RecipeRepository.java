package com.masalaboratory.vegetable.repository;

import com.masalaboratory.vegetable.model.Recipe;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Integer>{

}