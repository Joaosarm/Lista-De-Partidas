package br.com.joaosarmento.listadepartidasapi.DTOs;

import java.time.LocalDateTime;

public class PartidaDTO {

    private long id;
    private String clubeCasa;
    private String clubeVisitante;
    private int golsTimeCasa;
    private int golsTimeVisitante;
    private LocalDateTime dataDaPartida;
    private String estadioDaPartida;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
