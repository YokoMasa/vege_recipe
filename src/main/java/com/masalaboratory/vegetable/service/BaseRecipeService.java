package com.masalaboratory.vegetable.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.masalaboratory.vegetable.model.Identifiable;
import com.masalaboratory.vegetable.model.Recipe;

public abstract class BaseRecipeService {
    
    protected void sortField(Recipe r) {
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