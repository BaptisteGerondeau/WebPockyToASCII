package com.pocky.webpockytoascii;

import com.pocky.webpockytoascii.model.Image.Image;
import com.pocky.webpockytoascii.model.Image.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(ImageRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(Image.builder().name("pocky").type("JPEG").imageData(null).build()));
        };
    }
}