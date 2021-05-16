package ru.kpfu.itis.generation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.kpfu.itis.generation.service.DocumentServiceImpl;

@SpringBootApplication
public class DocumentGenerationServiceApplication {

    public static void main(String[] args) {
        DocumentServiceImpl.generateReport();
//        SpringApplication.run(DocumentGenerationServiceApplication.class, args);
    }

}
