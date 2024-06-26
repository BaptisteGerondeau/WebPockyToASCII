package com.pocky.webpockytoascii.model.ASCIIMatrix;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ASCIIMatrixValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ASCIIMatrix.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "imageId", "imageId is empty");
        ValidationUtils.rejectIfEmpty(errors, "type", "type is empty");
        ASCIIMatrix image = (ASCIIMatrix) target;
        if (image.getMatrixData().length > 409600) {
            errors.rejectValue("MatrixData", "size is too large");
        }
    }
}
