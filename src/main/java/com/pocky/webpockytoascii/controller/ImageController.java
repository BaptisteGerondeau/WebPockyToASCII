package com.pocky.webpockytoascii.controller;

import com.pocky.webpockytoascii.model.Image.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private com.pocky.webpockytoascii.service.ImageService ImageService;

    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        String response = ImageService.uploadImage(file);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllImages() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ImageService.findAllImages());
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updateImage(@PathVariable long id, @RequestParam("image") MultipartFile file) throws IOException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ImageService.updateImage(id, file));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ImageService.deleteImage(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getImageByName(@PathVariable("id") long id){
        byte[] image = ImageService.getImageData(id);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/jpeg"))
                .body(image);
    }

    @GetMapping("/ids/{name}")
    public ResponseEntity<?> getIdByName(@PathVariable("name") String name){
        ArrayList<Image> image = ImageService.getIdByName(name);

        return ResponseEntity.status(HttpStatus.OK)
                .body(image);
    }
}
