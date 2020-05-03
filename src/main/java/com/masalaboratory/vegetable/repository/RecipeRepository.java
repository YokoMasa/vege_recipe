package com.masalaboratory.vegetable.repository;

import com.masalaboratory.vegetable.model.Recipe;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

    public Page<Recipe> findAllByOrderByCreateDateDesc(Pageable pageable);

    public Page<Recipe> findAllByStatusOrderByCreateDateDesc(int status, Pageable pageable);

}