package br.com.joaosarmento.listadepartidasapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Date;
import java.sql.Time;

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
    private Date dataDaPartida;
    @Column
    private Time horaDaPartida;
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
