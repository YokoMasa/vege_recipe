package com.masalaboratory.vegetable.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.masalaboratory.vegetable.model.Identifiable;
import com.masalaboratory.vegetable.model.Recipe;
import com.masalaboratory.vegetable.repository.RecipeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public Page<Recipe> getPage(int pageNumber) {
        Pageable pageable = recipeRepository.getPagable(pageNumber);
        return recipeRepository.findAll(pageable);
    }

    @Override
    public Recipe getById(int id) {
        Optional<Recipe> oRecipe = recipeRepository.findById(id);
        if (oRecipe.isPresent()) {
            Recipe r = oRecipe.get();
            sortField(r);
            return r;
        } else {
            return null;
        }
    }

    @Override
    public Recipe create(Recipe recipe) {
        recipe.setCreateDate(new Date());
        return recipeRepository.saveAndFlush(recipe);
    }

    @Override
    public Recipe update(Recipe newRecipe) {
        return recipeRepository.saveAndFlush(newRecipe);
    }

    @Override
    public void delete(int id) {
        recipeRepository.deleteById(id);
    }

    private void sortField(Recipe r) {
        if (r.getIngredientOrder() != null && !r.getIngredientOrder().isEmpty()) {
            List<String> indecies = Arrays.asList(r.getIngredientOrder().split(","));
            r.getIngredients().sort(new IdentifiableComparator(indecies));
        }
        if (r.getRecipeProcOrder() != null && !r.getRecipeProcOrder().isEmpty()) {
            List<String> indecies = Arrays.asList(r.getRecipeProcOrder().split(","));
            r.getRecipeProcs().sort(new IdentifiableComparator(indecies));
        }
    }

    class IdentifiableComparator implements Comparator<Identifiable> {

        private List<String> indecies;

        @Override
        public int compare(Identifiable o1, Identifiable o2) {
            String idStr1 = Integer.toString(o1.getId());
            int index1 = indecies.indexOf(idStr1);
            String idStr2 = Integer.toString(o2.getId());
            int index2 = indecies.indexOf(idStr2);

            if (index1 == index2) {
                return 0;
            }
            return index1 < index2 ? -1 : 1;
        }

        IdentifiableComparator(List<String> indecies) {
            this.indecies = indecies;
        }
        
    }

}