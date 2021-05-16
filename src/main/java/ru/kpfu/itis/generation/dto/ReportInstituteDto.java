package ru.kpfu.itis.generation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportInstituteDto {
    @NotBlank
    private String name;

    @NotNull
    private Long inn;

    public String toString() {
        return this.name + " (ИНН: " + this.inn + ")";
    }
}
