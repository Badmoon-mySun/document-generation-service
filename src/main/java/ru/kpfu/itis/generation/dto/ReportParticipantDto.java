package ru.kpfu.itis.generation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportParticipantDto {

    @NotNull
    @JsonFormat(pattern="HH:mm:ss dd.MM.yyyy")
    private Date formed;

    @NotNull
    @JsonFormat(pattern="HH:mm:ss dd.MM.yyyy")
    private Date decorated;

    @NotNull
    @JsonFormat(pattern="HH:mm:ss dd.MM.yyyy")
    private Date enrolled;

    @NotBlank
    private String comment;

    @NotBlank
    private String name;

    @NotBlank
    private String position;

    @NotBlank
    private String ipAddress;
}
