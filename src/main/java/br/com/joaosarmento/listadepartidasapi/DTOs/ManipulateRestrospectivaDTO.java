package br.com.joaosarmento.listadepartidasapi.DTOs;

import java.util.Optional;

public class ManipulateRestrospectivaDTO {

    private Integer vitorias;
    private Integer derrotas;
    private Integer empates;
    private Integer golsPro;
    private Integer golsContra;

    public ManipulateRestrospectivaDTO(){
        this.vitorias = 0;
        this.derrotas = 0;
        this.empates = 0;
        this.golsPro = 0;
        this.golsContra = 0;
    }

    public ManipulateRestrospectivaDTO(RetrospectivaDTO retrospectivaCasa){
            this.vitorias = retrospectivaCasa.getVitorias();
            this.derrotas = retrospectivaCasa.getDerrotas();
            this.empates = retrospectivaCasa.getEmpates();
            this.golsPro = retrospectivaCasa.getGolsPro();
            this.golsContra = retrospectivaCasa.getGolsContra();
    }

    public ManipulateRestrospectivaDTO(ManipulateRestrospectivaDTO retrospectivaCasa, ManipulateRestrospectivaDTO retrospectivaVisitante){
        this.vitorias = retrospectivaCasa.getVitorias() + retrospectivaVisitante.getVitorias();
        this.derrotas = retrospectivaCasa.getDerrotas() + retrospectivaVisitante.getDerrotas();
        this.empates = retrospectivaCasa.getEmpates() + retrospectivaVisitante.getEmpates();
        this.golsPro = retrospectivaCasa.getGolsPro() + retrospectivaVisitante.getGolsPro();
        this.golsContra = retrospectivaCasa.getGolsContra() + retrospectivaVisitante.getGolsContra();
    }


    public int getVitorias() {
        return vitorias;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public int getEmpates() {
        return empates;
    }

    public int getGolsPro() {
        return golsPro;
    }

    public int getGolsContra() {
        return golsContra;
    }
}
