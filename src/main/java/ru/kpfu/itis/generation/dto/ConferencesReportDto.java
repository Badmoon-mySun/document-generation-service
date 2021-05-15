package ru.kpfu.itis.generation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConferencesReportDto {
    private String fileName;

    private Long id;
    private String type;
    private String login;
    private String institute;
    private List<String> accordingTo;
    private Integer studentsCount;
}
