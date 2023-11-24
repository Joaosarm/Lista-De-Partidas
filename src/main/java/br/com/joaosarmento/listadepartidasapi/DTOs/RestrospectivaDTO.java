package br.com.joaosarmento.listadepartidasapi.DTOs;

import lombok.Getter;

@Getter
public class RestrospectivaDTO {
    private Integer vitorias;
    private Integer derrotas;
    private Integer empates;
    private Integer golsPro;
    private Integer golsContra;

    public RestrospectivaDTO(){
        this.vitorias = 0;
        this.derrotas = 0;
        this.empates = 0;
        this.golsPro = 0;
        this.golsContra = 0;
    }

    public RestrospectivaDTO(Retrospectiva retrospectivaCasa){
            this.vitorias = retrospectivaCasa.getVitorias();
            this.derrotas = retrospectivaCasa.getDerrotas();
            this.empates = retrospectivaCasa.getEmpates();
            this.golsPro = retrospectivaCasa.getGolsPro();
            this.golsContra = retrospectivaCasa.getGolsContra();
    }

    public RestrospectivaDTO MergeRetrospectivasPorClube(RestrospectivaDTO retrospectivaCasa, RestrospectivaDTO retrospectivaVisitante){
        this.vitorias = retrospectivaCasa.getVitorias() + retrospectivaVisitante.getVitorias();
        this.derrotas = retrospectivaCasa.getDerrotas() + retrospectivaVisitante.getDerrotas();
        this.empates = retrospectivaCasa.getEmpates() + retrospectivaVisitante.getEmpates();
        this.golsPro = retrospectivaCasa.getGolsPro() + retrospectivaVisitante.getGolsPro();
        this.golsContra = retrospectivaCasa.getGolsContra() + retrospectivaVisitante.getGolsContra();

        return this;
    }
}
