package com.example.spring_notes_api.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class Utils {
    public static String getValidationErrorMessages(BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        String validationErrors = fieldErrors.getFirst().getDefaultMessage();

        if (fieldErrors.size() > 1) {
            for (int i = 1; i < fieldErrors.size(); i++) {
                validationErrors += ", " + fieldErrors.get(i).getDefaultMessage();
            }
        }

        return validationErrors;
    }
}
