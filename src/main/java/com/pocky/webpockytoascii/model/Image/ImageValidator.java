package com.pocky.webpockytoascii.model.Image;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ImageValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Image.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "name", "name is empty");
        ValidationUtils.rejectIfEmpty(errors, "type", "type is empty");
        Image image = (Image) target;
        if (image.getImageData().length > 409600) {
            errors.rejectValue("imageData", "size is too large");
        }
    }
}
