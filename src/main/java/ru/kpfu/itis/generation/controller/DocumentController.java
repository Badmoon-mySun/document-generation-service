package ru.kpfu.itis.generation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.generation.dto.ConferencesReportDto;
import ru.kpfu.itis.generation.dto.ReportsHandlerDto;
import ru.kpfu.itis.generation.service.DocumentService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@RestController
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping(value = "/generate", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public void generateDocument(@RequestBody @Valid ReportsHandlerDto reportsHandler, HttpServletResponse response) {
        response.setContentType("application/pdf");

        try {
            OutputStream outputStream = response.getOutputStream();

            documentService.generateReport(reportsHandler.getReports(), outputStream);

            outputStream.flush();
        } catch (IOException ex) {
            throw new RuntimeException("Error writing file to output stream");
        }

    }
}
