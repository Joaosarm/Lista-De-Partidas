package br.com.joaosarmento.listadepartidasapi.DTOs;

import lombok.Getter;

@Getter
public class RetrospectivaPorConfrontoDTO {

    private Integer vitoriasPrimeiroTime;
    private Integer vitoriasSegundoTime;
    private Integer empates;
    private Integer golsPrimeiroTime;
    private Integer golsSegundoTime;

    public RetrospectivaPorConfrontoDTO RetrospectivaPrimeiroClube(RestrospectivaDTO retrospectivaClubeCasa) {
        this.vitoriasPrimeiroTime = retrospectivaClubeCasa.getVitorias();
        this.vitoriasSegundoTime = retrospectivaClubeCasa.getDerrotas();
        this.empates = retrospectivaClubeCasa.getEmpates();
        this.golsPrimeiroTime = retrospectivaClubeCasa.getGolsPro();
        this.golsSegundoTime = retrospectivaClubeCasa.getGolsContra();

        return this;
    }

    public RetrospectivaPorConfrontoDTO RetrospectivaSegundoClube(RestrospectivaDTO retrospectivaClubeCasa) {
        this.vitoriasPrimeiroTime = retrospectivaClubeCasa.getDerrotas();
        this.vitoriasSegundoTime = retrospectivaClubeCasa.getVitorias();
        this.empates = retrospectivaClubeCasa.getEmpates();
        this.golsPrimeiroTime = retrospectivaClubeCasa.getGolsContra();
        this.golsSegundoTime = retrospectivaClubeCasa.getGolsPro();

        return this;
    }

    public RetrospectivaPorConfrontoDTO MergeRetrospectivasConfronto(RestrospectivaDTO retrospectivaPrimeiroClubeCasa, RestrospectivaDTO retrospectivaSegundoClubeCasa){
        this.vitoriasPrimeiroTime = retrospectivaPrimeiroClubeCasa.getVitorias() + retrospectivaSegundoClubeCasa.getDerrotas();
        this.vitoriasSegundoTime = retrospectivaPrimeiroClubeCasa.getDerrotas() + retrospectivaSegundoClubeCasa.getVitorias();
        this.empates = retrospectivaPrimeiroClubeCasa.getEmpates() + retrospectivaSegundoClubeCasa.getEmpates();
        this.golsPrimeiroTime = retrospectivaPrimeiroClubeCasa.getGolsPro() + retrospectivaSegundoClubeCasa.getGolsContra();
        this.golsSegundoTime = retrospectivaPrimeiroClubeCasa.getGolsContra() + retrospectivaSegundoClubeCasa.getGolsPro();

        return this;
    }
}
