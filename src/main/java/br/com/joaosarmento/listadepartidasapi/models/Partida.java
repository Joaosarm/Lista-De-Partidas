package br.com.joaosarmento.listadepartidasapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Timestamp;

@Entity
@Table(name = "partida")
public class Partida {

    @Id
    @Column
    private long id;
    @Column
    private String clubeCasa;
    @Column
    private String clubeVisitante;
    @Column
    private int golsTimeCasa;
    @Column
    private int golsTimeVisitante;
    @Column
    private Timestamp dataDaPartida;
    @Column
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
