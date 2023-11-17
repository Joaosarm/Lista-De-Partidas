package br.com.joaosarmento.listadepartidasapi.DTOs;

import lombok.Getter;

import java.util.Comparator;

@Getter
public class ListaDeFreguesesDTO {
    private String clubeFregues;
    private Integer quantidadePartidas;
    private Integer quantidadeVitorias;
    private Integer quantidadeDerrotas;

    public ListaDeFreguesesDTO(String clubeFregues, Integer quantidadeVitorias, Integer quantidadeDerrotas, Integer quantidadeEmpates){
        this.clubeFregues = clubeFregues;
        this.quantidadePartidas = quantidadeVitorias + quantidadeDerrotas + quantidadeEmpates;
        this.quantidadeVitorias = quantidadeVitorias;
        this.quantidadeDerrotas = quantidadeDerrotas;
    }
}
