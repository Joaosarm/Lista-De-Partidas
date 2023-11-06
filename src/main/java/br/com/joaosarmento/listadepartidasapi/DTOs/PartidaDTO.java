package br.com.joaosarmento.listadepartidasapi.DTOs;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.sql.Date;
import java.sql.Time;

public class PartidaDTO {

    private long id;
    private String clubeCasa;
    private String clubeVisitante;
    private int golsTimeCasa;
    private int golsTimeVisitante;
    private Date dataDaPartida;
    private Time horaDaPartida;
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

    public Date getDataDaPartida() {
        return dataDaPartida;
    }

    public void setDataDaPartida(Date dataDaPartida) {
        this.dataDaPartida = dataDaPartida;
    }

    public Time getHoraDaPartida() {
        return horaDaPartida;
    }

    public void setHoraDaPartida(Time horaDaPartida) {
        this.horaDaPartida = horaDaPartida;
    }

    public String getEstadioDaPartida() {
        return estadioDaPartida;
    }

    public void setEstadioDaPartida(String estadioDaPartida) {
        this.estadioDaPartida = estadioDaPartida;
    }
}
