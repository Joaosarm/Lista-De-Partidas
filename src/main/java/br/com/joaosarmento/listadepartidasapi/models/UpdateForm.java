package br.com.joaosarmento.listadepartidasapi.models;

import java.sql.Timestamp;

public class UpdateForm {
    private String clubeCasa;
    private String clubeVisitante;
    private int golsTimeCasa;
    private int golsTimeVisitante;
    private Timestamp dataDaPartida;
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

    public Timestamp getDataDaPartida() {
        return dataDaPartida;
    }

    public void setDataDaPartida(Timestamp dataDaPartida) {
        this.dataDaPartida = dataDaPartida;
    }

    public String getEstadioDaPartida() {
        return estadioDaPartida;
    }

    public void setEstadioDaPartida(String estadioDaPartida) {
        this.estadioDaPartida = estadioDaPartida;
    }
}
