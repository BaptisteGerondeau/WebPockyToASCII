package com.pocky.webpockytoascii.service;

import com.pocky.webpockytoascii.model.ASCIIMatrix.ASCIIMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.pocky.pockytoascii.*;

import javax.imageio.ImageIO;

@Service
@Transactional
public class ASCIIMatrixService {

    @Autowired
    private com.pocky.webpockytoascii.model.ASCIIMatrix.ASCIIMatrixRespository ASCIIMatrixRepository;

    @Autowired
    private com.pocky.webpockytoascii.service.ImageService ImageService;

    public ASCIIMatrix convertImageToASCIIMatrix(long imageId, String type, String key, int dwidth, boolean invert)
            throws ImageHandlingException, ImageHandlingException {

        Renderer converter;
        switch (type.toLowerCase(Locale.ROOT)) {
            case "html":
                converter = new HTMLRenderer();
                break;
            case "ansi":
                converter = new ANSIRenderer();
                break;
            case "raw":
                converter = new RawASCIIRenderer();
                break;
            default:
                throw new IllegalArgumentException("Unsupported type: " + type);
        }

        byte[] data = ImageService.getImageData(imageId);
        BufferedImage image = null;
        if (data != null) {
            try {
                image = ImageIO.read(new ByteArrayInputStream(data));
            } catch (IOException exception) {
                throw new ImageHandlingException(exception.getMessage());
            }
        } else {
            throw new IllegalArgumentException("Image not found: " + imageId);
        }

        PixelMatrix pxmatrix = new PixelMatrix(image, dwidth, -1);
        return ASCIIMatrixRepository.save(ASCIIMatrix.builder()
                .imageId(imageId)
                .type(type)
                .matrixData(converter.render(pxmatrix, key, invert, PixelMatrix.BRIGHTNESS_MODE.AVERAGE).getBytes())
                .build());
    }

    public String deleteASCIIMatrix(long id) throws NoSuchElementException {
        Optional<ASCIIMatrix> dbMatrix = ASCIIMatrixRepository.findById(id);
        if(dbMatrix.isPresent()) {
            ASCIIMatrixRepository.deleteById(id);
            return "ASCII Matrix deleted successfully";
        }

        throw new NoSuchElementException("No ASCII Matrix found with id: " + id);
    }

    public ArrayList<ASCIIMatrix> getIdByImageId(long imageId) throws NoSuchElementException {
        Optional<ArrayList<ASCIIMatrix>> dbMatrix = ASCIIMatrixRepository.findByImageId(imageId);

        ArrayList<ASCIIMatrix> result = new ArrayList<ASCIIMatrix>();
        if (dbMatrix.isPresent()) {
            for (ASCIIMatrix matrix : dbMatrix.get()) {
                result.add(ASCIIMatrix.builder()
                        .id(matrix.getId())
                        .imageId(matrix.getImageId())
                        .type(matrix.getType())
                        .build());
            }

            return result;
        }
        else {
            throw new NoSuchElementException("No ASCII Matrix found with imageid: " + imageId);
        }
    }

    public byte[] getMatrixData(long id) throws NoSuchElementException {
        Optional<ASCIIMatrix> dbImage = ASCIIMatrixRepository.findById(id);
        if (dbImage.isPresent() && dbImage.get().getMatrixData() != null) {
            return dbImage.get().getMatrixData();
        }
        else {
            throw new NoSuchElementException("No ASCII Matrix found with id: " + id);
        }
    }

    public Iterable<ASCIIMatrix> findAllImages() {
        return ASCIIMatrixRepository.findAll();
    }
}