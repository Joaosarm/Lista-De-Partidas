package br.com.joaosarmento.listadepartidasapi.DTOs;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartidaDTO {

    @NotBlank (message = "Não pode estar em branco.")
    private String clubeCasa;
    @NotBlank (message = "Não pode estar em branco.")
    private String clubeVisitante;
    @PositiveOrZero (message = "Não pode ser negativo.")
    private int golsTimeCasa;
    @PositiveOrZero (message = "Não pode ser negativo.")
    private int golsTimeVisitante;
    @PastOrPresent(message = "Não pode estar em branco")
    private LocalDateTime dataDaPartida;
    @NotBlank (message = "Não pode estar em branco")
    private String estadioDaPartida;
}
