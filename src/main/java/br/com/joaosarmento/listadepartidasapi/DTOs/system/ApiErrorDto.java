package br.com.joaosarmento.listadepartidasapi.DTOs.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorDto {
    private Integer status;
    private String code;
    private String message;
}
