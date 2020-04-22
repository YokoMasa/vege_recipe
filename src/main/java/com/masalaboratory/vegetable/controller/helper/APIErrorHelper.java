package com.masalaboratory.vegetable.controller.helper;

import java.util.ArrayList;
import java.util.List;

import com.masalaboratory.vegetable.controller.APIError;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Component
public class APIErrorHelper {

    public class APIFieldError {
        public final String name;
        public final String message;

        public APIFieldError(String name, String message) {
            this.name = name;
            this.message = message;
        }

        @Override
        public String toString() {
            return "name: " + name + ", message: " + message;
        }
    }

    public APIError<List<APIFieldError>> getValidationError(BindingResult br) {
        List<APIFieldError> apiFieldErrors = new ArrayList<>();
        for (FieldError fieldError: br.getFieldErrors()) {
            apiFieldErrors.add(new APIFieldError(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return new APIError<List<APIFieldError>>("validation error!!", apiFieldErrors);
    }

}