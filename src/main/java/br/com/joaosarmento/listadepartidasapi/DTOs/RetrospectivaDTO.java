package br.com.joaosarmento.listadepartidasapi.DTOs;

public class RetrospectivaDTO {
    private Long vitorias;
    private Long derrotas;
    private Long empates;
    private int golsPro;
    private int golsContra;

    public RetrospectivaDTO(Long vitorias, Long derrotas, Long empates, int golsPro, int golsContra){
        this.vitorias = vitorias;
        this.derrotas = derrotas;
        this.empates = empates;
        this.golsPro = golsPro;
        this.golsContra = golsContra;
    }

    public Long getVitorias() {
        return vitorias;
    }

    public Long getDerrotas() {
        return derrotas;
    }

    public Long getEmpates() {
        return empates;
    }

    public int getGolsPro() {
        return golsPro;
    }

    public int getGolsContra() {
        return golsContra;
    }
}
