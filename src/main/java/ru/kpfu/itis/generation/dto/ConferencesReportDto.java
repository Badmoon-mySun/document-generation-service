package ru.kpfu.itis.generation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
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
    @NotNull
    private List<String> accordingTo;

    @NotNull
    private Long id;

    @NotBlank
    private String type;

    @NotBlank
    private String login;

    @Valid
    @NotNull
    private ReportInstituteDto institute;

    @NotNull
    private Integer studentsCount;

    @NotNull
    @JsonFormat(pattern="HH:mm:ss dd.MM.yyyy")
    private Date conferenceTime;

    @Valid
    @NotNull
    private List<ReportParticipantDto> participants;

    private String note;
}
