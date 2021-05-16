package ru.kpfu.itis.generation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
public class ReportsHandlerDto {

    @Valid
    @NotNull
    private List<ConferencesReportDto> reports;
}
