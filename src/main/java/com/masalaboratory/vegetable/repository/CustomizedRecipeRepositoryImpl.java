package com.masalaboratory.vegetable.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class CustomizedRecipeRepositoryImpl implements CustomizedRecipeRepository {

    private static final int PAGE_SIZE = 12;

    @Override
    public Pageable getPagable(int page) {
        return PageRequest.of(page, PAGE_SIZE, Sort.by(Direction.DESC, "createDate"));
    }

}