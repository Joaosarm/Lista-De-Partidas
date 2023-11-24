package br.com.joaosarmento.listadepartidasapi.DTOs;

public class ConfrontoDTO {
    private String primeiroClube;
    private String segundoClube;
    private String clubeMandante;

    public ConfrontoDTO(String primeiroClube, String segundoClube){
        this.primeiroClube = primeiroClube;
        this.segundoClube = segundoClube;
    }

    public String getPrimeiroClube() {
        return primeiroClube;
    }

    public String getSegundoClube() {
        return segundoClube;
    }

    public String getClubeMandante() {
        return clubeMandante;
    }
}
