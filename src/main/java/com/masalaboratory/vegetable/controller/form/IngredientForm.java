package com.masalaboratory.vegetable.controller.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class IngredientForm {

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotBlank
    @Size(max = 255)
    private String quantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

}