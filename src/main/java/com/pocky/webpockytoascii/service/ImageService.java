package com.pocky.webpockytoascii.service;

import com.pocky.webpockytoascii.model.Image.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional
public class ImageService {

    @Autowired
    private com.pocky.webpockytoascii.model.Image.ImageRepository ImageRepository;

    public String uploadImage(MultipartFile file) throws IOException {

        ImageRepository.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtil.compressImage(file.getBytes())).build());

        return "Image uploaded successfully: " + file.getOriginalFilename();

    }

    public String updateImage(long id, MultipartFile file) throws IOException {
        Optional<Image> dbImage = ImageRepository.findById(id);
        if(dbImage.isPresent()){
            try {
                dbImage.get().setImageData(file.getBytes());
                return "Image updated successfully: " + file.getOriginalFilename();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            return uploadImage(file);
        }
    }

    public String deleteImage(long id) {
        Optional<Image> dbImage = ImageRepository.findById(id);
        if(dbImage.isPresent()) {
            ImageRepository.deleteById(id);
            return "Image deleted successfully";
        }

        return "Image not found";
    }

    public ArrayList<Image> getIdByName(String name) {
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
            return null;
        }
    }

    public byte[] getImageData(long id) {
        Optional<Image> dbImage = ImageRepository.findById(id);
        if (dbImage.isPresent() && dbImage.get().getImageData() != null) {
            return ImageUtil.decompressImage(dbImage.get().getImageData());
        }
        else {
            return null;
        }
    }

    public Iterable<Image> findAllImages() {
        return ImageRepository.findAll();
    }
}