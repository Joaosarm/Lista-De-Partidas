package br.com.joaosarmento.listadepartidasapi.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;

public class UpdateFormDTO {
    @NotBlank(message = "clubeCasa não pode estar em branco!")
    private String clubeCasa;
    @NotBlank(message = "clubeVisitante não pode estar em branco")
    private String clubeVisitante;
    @PositiveOrZero(message = "golsTimeCasa não pode ser nulo")
    private int golsTimeCasa;
    @PositiveOrZero(message = "golsTimeVisitante não pode ser nulo")
    private int golsTimeVisitante;
    @NotNull(message = "dataDaPartida não pode estar em branco")
    private LocalDateTime dataDaPartida;
    @NotBlank(message = "estadioDaPartida não pode estar em branco")
    private String estadioDaPartida;

    public String getClubeCasa() {
        return clubeCasa;
    }

    public void setClubeCasa(String clubeCasa) {
        this.clubeCasa = clubeCasa;
    }

    public String getClubeVisitante() {
        return clubeVisitante;
    }

    public void setClubeVisitante(String clubeVisitante) {
        this.clubeVisitante = clubeVisitante;
    }

    public int getGolsTimeCasa() {
        return golsTimeCasa;
    }

    public void setGolsTimeCasa(int golsTimeCasa) {
        this.golsTimeCasa = golsTimeCasa;
    }

    public int getGolsTimeVisitante() {
        return golsTimeVisitante;
    }

    public void setGolsTimeVisitante(int golsTimeVisitante) {
        this.golsTimeVisitante = golsTimeVisitante;
    }

    public LocalDateTime getDataDaPartida() {
        return dataDaPartida;
    }

    public void setDataDaPartida(LocalDateTime dataDaPartida) {
        this.dataDaPartida = dataDaPartida;
    }

    public String getEstadioDaPartida() {
        return estadioDaPartida;
    }

    public void setEstadioDaPartida(String estadioDaPartida) {
        this.estadioDaPartida = estadioDaPartida;
    }
}
