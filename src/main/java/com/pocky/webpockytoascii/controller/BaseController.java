package com.pocky.webpockytoascii.controller;

import com.pocky.webpockytoascii.service.ImageHandlingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

public class BaseController {

    private static final Logger log = LoggerFactory.getLogger(ImageController.class);

    @ExceptionHandler(ImageHandlingException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public String handleImageHandlingException(ImageHandlingException e) {
        log.error(e.getMessage());
        return "Image handling error";
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoSuchElementException(NoSuchElementException e) {
        log.error(e.getMessage());
        return "Image not found";
    }
}
