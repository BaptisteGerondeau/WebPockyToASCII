package com.pocky.webpockytoascii.service;

import com.pocky.webpockytoascii.model.Image.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class ImageService {

    @Autowired
    private com.pocky.webpockytoascii.model.Image.ImageRepository ImageRepository;

    public Image uploadImage(MultipartFile file) throws ImageHandlingException {
        try {
            return ImageRepository.save(Image.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .imageData(ImageUtil.compressImage(file.getBytes())).build());
        } catch (IOException e) {
            throw new ImageHandlingException(e.getMessage());
        }
    }

    public Image updateImage(long id, MultipartFile file) throws ImageHandlingException {
        Optional<Image> dbImage = ImageRepository.findById(id);
        if(dbImage.isPresent()){
            try {
                dbImage.get().setImageData(file.getBytes());
                return dbImage.get();
            } catch (IOException e) {
                throw new ImageHandlingException(e.getMessage());
            }
        }
        else {
            return uploadImage(file);
        }
    }

    public String deleteImage(long id) throws NoSuchElementException {
        Optional<Image> dbImage = ImageRepository.findById(id);
        if(dbImage.isPresent()) {
            ImageRepository.deleteById(id);
            return "Image deleted successfully";
        }
        else {
            throw new NoSuchElementException("Image not found");
        }
    }

    public ArrayList<Image> getIdByName(String name) throws NoSuchElementException {
        Optional<ArrayList<Image>> dbImage = ImageRepository.findByName(name);
        
        ArrayList<Image> result = new ArrayList<Image>();
        if (dbImage.isPresent()) {
            for (Image image : dbImage.get()) {
                result.add(Image.builder()
                        .id(image.getId())
                        .name(image.getName())
                        .type(image.getType())
                        .build());
            }

            return result;
        }
        else {
            throw new NoSuchElementException("Image not found");
        }
    }

    public byte[] getImageData(long id) throws NoSuchElementException {
        Optional<Image> dbImage = ImageRepository.findById(id);
        if (dbImage.isPresent() && dbImage.get().getImageData() != null) {
            return ImageUtil.decompressImage(dbImage.get().getImageData());
        }
        else {
            throw new NoSuchElementException("Image not found");
        }
    }

    public List<Image> findAllImages() {
        //Needs paging
        return ImageRepository.findAll();
    }
}