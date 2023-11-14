package br.com.joaosarmento.listadepartidasapi.DTOs;

public class ManipulateRestrospectivaDTO {

    private Long vitorias;
    private Long derrotas;
    private Long empates;
    private int golsPro;
    private int golsContra;

    public ManipulateRestrospectivaDTO(RetrospectivaDTO retrospectivaCasa, RetrospectivaDTO retrospectivaVisitante){
        this.vitorias = retrospectivaCasa.getVitorias() + retrospectivaVisitante.getVitorias();
        this.derrotas = retrospectivaCasa.getDerrotas() + retrospectivaVisitante.getDerrotas();
        this.empates = retrospectivaCasa.getEmpates() + retrospectivaVisitante.getEmpates();
        this.golsPro = retrospectivaCasa.getGolsPro() + retrospectivaVisitante.getGolsPro();
        this.golsContra = retrospectivaCasa.getGolsContra() + retrospectivaVisitante.getGolsContra();
    }
}
