package com.pocky.webpockytoascii.model.ASCIIMatrix;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface ASCIIMatrixRespository extends JpaRepository<ASCIIMatrix, Long> {
    Optional<ArrayList<ASCIIMatrix>> findByImageId(long imageId);
}
