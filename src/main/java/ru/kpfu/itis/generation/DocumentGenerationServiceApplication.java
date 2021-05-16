package ru.kpfu.itis.generation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.kpfu.itis.generation.dto.ConferencesReportDto;
import ru.kpfu.itis.generation.dto.ReportInstituteDto;
import ru.kpfu.itis.generation.dto.ReportParticipantDto;
import ru.kpfu.itis.generation.service.DocumentServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class DocumentGenerationServiceApplication {

    public static void main(String[] args) {
//        List<ReportParticipantDto> list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            ReportParticipantDto participant = ReportParticipantDto.builder()
//                    .formed(new Date(System.currentTimeMillis()))
//                    .decorated(new Date(System.currentTimeMillis()))
//                    .enrolled(new Date(System.currentTimeMillis()))
//                    .comment("Какой-нибудь текст, Какой-нибудь длинный, а может быть не очень длинный текст, главное чтобы верстка не полетела Версия")
//                    .ipAddress("192.168.0.1")
//                    .name("Khasanov Anvar Rustamovich")
//                    .position("Student")
//                    .build();
//
//            list.add(participant);
//        }
//
//        List<String> according = new ArrayList<>();
//        according.add("000123");
//        according.add("000123");
//        according.add("000123");
//        according.add("000123");
//
//        ConferencesReportDto report = ConferencesReportDto.builder()
//                .id(123L)
//                .conferenceTime(new Date(System.currentTimeMillis()))
//                .accordingTo(according)
//                .institute(new ReportInstituteDto("ITIS", 12321321L))
//                .note("asadsadasdsadas")
//                .studentsCount(65)
//                .login("Login")
//                .type("TT")
//                .participants(list)
//                .build();
//
//        DocumentServiceImpl documentService = new DocumentServiceImpl();
//
//        documentService.generateReport(report);

        SpringApplication.run(DocumentGenerationServiceApplication.class, args);
    }

}
