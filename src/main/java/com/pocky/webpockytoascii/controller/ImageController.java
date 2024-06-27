package com.pocky.webpockytoascii.controller;

import com.pocky.webpockytoascii.model.Image.Image;
import com.pocky.webpockytoascii.service.ImageHandlingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/images")
public class ImageController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private com.pocky.webpockytoascii.service.ImageService ImageService;

    @PostMapping
    public Image uploadImage(@RequestParam("image") MultipartFile file) throws ImageHandlingException {
        return ImageService.uploadImage(file);
    }

    @GetMapping
    public List<Image> getAllImages() {
        return ImageService.findAllImages();
    }

    @PostMapping("/{id}")
    public Image updateImage(@PathVariable long id, @RequestParam("image") MultipartFile file) throws ImageHandlingException {
        return ImageService.updateImage(id, file);
    }

    @DeleteMapping("/{id}")
    public String deleteImage(@PathVariable long id) throws NoSuchElementException {
        return ImageService.deleteImage(id);
    }

    @GetMapping(value="/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImageDataById(@PathVariable("id") long id) throws NoSuchElementException {
        return ImageService.getImageData(id);
    }

    @GetMapping("/ids/{name}")
    public ArrayList<Image> getIdByName(@PathVariable("name") String name) throws NoSuchElementException {
        return ImageService.getIdByName(name);
    }
}
