package ru.kpfu.itis.generation.service;

import ru.kpfu.itis.generation.dto.ConferencesReportDto;

import java.io.OutputStream;
import java.util.List;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
public interface DocumentService {
    void generateReport(List<ConferencesReportDto> reportDtoList, OutputStream outputStream);
}
