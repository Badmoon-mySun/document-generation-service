package ru.kpfu.itis.generation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@RestController
@RequestMapping("/document")
public class DocumentController {


    @PostMapping("/generate")
    public void generateDocument() {

    }
}
