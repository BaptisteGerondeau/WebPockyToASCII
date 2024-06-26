package com.pocky.webpockytoascii.model.Image;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<ArrayList<Image>> findByName(String name);
}