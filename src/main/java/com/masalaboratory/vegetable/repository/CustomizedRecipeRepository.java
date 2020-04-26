package com.masalaboratory.vegetable.repository;

import org.springframework.data.domain.Pageable;

public interface CustomizedRecipeRepository {

    public Pageable getPagable(int page);

}