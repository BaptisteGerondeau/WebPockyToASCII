package com.pocky.webpockytoascii.controller;

import com.pocky.webpockytoascii.model.ASCIIMatrix.ASCIIMatrix;
import com.pocky.webpockytoascii.service.ASCIIMatrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/asciiart")
public class ASCIIMatrixController {

    @Autowired
    private ASCIIMatrixService AsciiMatrixService = new ASCIIMatrixService();

    @PostMapping
    public ResponseEntity<?> requestASCIIMatrix(@RequestParam("imageid") long imageid, @RequestParam("type") String type) throws IOException {
        String response = String.valueOf(AsciiMatrixService.convertImageToASCIIMatrix(imageid, type));

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllMatrices() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(AsciiMatrixService.findAllImages());
    }

//    @PostMapping("/{id}")
//    public ResponseEntity<?> updateImage(@PathVariable long id, @RequestParam("type") String type) {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(AsciiMatrixService.);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAscii(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(AsciiMatrixService.deleteASCIIMatrix(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAscii(@PathVariable("id") long id){
        byte[] ascii = AsciiMatrixService.getMatrixData(id);
        String art = new String(ascii);

        return ResponseEntity.status(HttpStatus.OK)
                .body(art);
    }

    @GetMapping("/ids/{imageid}")
    public ResponseEntity<?> getIdByImageId(@PathVariable("imageid") long id){
        ArrayList<ASCIIMatrix> matrices = AsciiMatrixService.getIdByImageId(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(matrices);
    }
}
