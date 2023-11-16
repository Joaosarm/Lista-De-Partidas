package br.com.joaosarmento.listadepartidasapi.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PartidaDTO {

    @NotBlank(message = "clubeCasa não pode estar em branco!")
    private String clubeCasa;
    @NotBlank(message = "clubeVisitante não pode estar em branco")
    private String clubeVisitante;
    @PositiveOrZero(message = "golsTimeCasa não pode ser nulo")
    private int golsTimeCasa;
    @PositiveOrZero(message = "golsTimeVisitante não pode ser nulo")
    private int golsTimeVisitante;
    @PastOrPresent(message = "dataDaPartida não pode estar em branco")
    private LocalDateTime dataDaPartida;
    @NotBlank(message = "estadioDaPartida não pode estar em branco")
    private String estadioDaPartida;
}
