package com.pocky.webpockytoascii.controller;

import com.pocky.webpockytoascii.model.ASCIIMatrix.ASCIIMatrix;
import com.pocky.webpockytoascii.service.ASCIIMatrixService;
import com.pocky.webpockytoascii.service.ImageHandlingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/asciiart")
public class ASCIIMatrixController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(ASCIIMatrixController.class);

    @Autowired
    private ASCIIMatrixService AsciiMatrixService = new ASCIIMatrixService();

    @PostMapping
    public ASCIIMatrix requestASCIIMatrix(@RequestParam(value="imageid") long imageid,
                                          @RequestParam(value="type", defaultValue = "raw") String type,
                                          @RequestParam(value="key", defaultValue = "`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$") String key,
                                          @RequestParam(value="dwidth", defaultValue = "94") int dwidth,
                                          @RequestParam(value="invert", defaultValue = "false") boolean invert)
            throws IllegalArgumentException, ImageHandlingException {
        return AsciiMatrixService.convertImageToASCIIMatrix(imageid, type, key, dwidth, invert);
    }

    @GetMapping
    public Iterable<ASCIIMatrix> getAllMatrices() {
        return AsciiMatrixService.findAllImages();
    }

    @DeleteMapping("/{id}")
    public String deleteAscii(@PathVariable long id) throws NoSuchElementException {
        return AsciiMatrixService.deleteASCIIMatrix(id);
    }

    @GetMapping("/{id}")
    public byte[] getAscii(@PathVariable("id") long id) throws NoSuchElementException {
        return AsciiMatrixService.getMatrixData(id);
    }

    @GetMapping("/ids/{imageid}")
    public ArrayList<ASCIIMatrix> getIdByImageId(@PathVariable("imageid") long id) throws NoSuchElementException {
        return AsciiMatrixService.getIdByImageId(id);
    }
}
