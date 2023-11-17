package br.com.joaosarmento.listadepartidasapi.controllers;

import br.com.joaosarmento.listadepartidasapi.DTOs.*;
import br.com.joaosarmento.listadepartidasapi.models.Partida;
import br.com.joaosarmento.listadepartidasapi.services.PartidaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class PartidaController {

    @Autowired
    private PartidaService partidaService;

    @PostMapping
    public String postPartida(@Valid @RequestBody PartidaDTO partidaDTO){
        return partidaService.postPartida(partidaDTO);
    }

    @GetMapping
    public List<Partida> getTodasAsPartidas(){
        return partidaService.getTodasAsPartidas();
    }

    @GetMapping("/{id}")
    public Optional<Partida> getUmaPartida(@PathVariable Long id){
        return partidaService.getUmaPartida(id);
    }

    @GetMapping("/estadio")
    public List<Partida> getPartidaPorEstadio(@RequestBody EstadioDTO estadioDaPartida){ return partidaService.getPartidaPorEstadio(estadioDaPartida.getEstadioDaPartida()); }

    @GetMapping("/partidas-com-goleadas")
    public List<Partida> getPartidasComGoleadas(){
        return partidaService.getPartidasComGoleadas();
    }

    @GetMapping("/partidas-sem-gols")
    public List<Partida> getPartidasSemGols(){
        return partidaService.getPartidasSemGols();
    }

    @GetMapping("/partidas-por-clube")
    public List<Partida> getPartidasComClube(@RequestBody ClubeDTO clubeDTO){ return partidaService.getPartidasComClube(clubeDTO); }

    @GetMapping("/partidas-por-clube-casa")
    public List<Partida> getPartidasComClubeCasa(@RequestBody ClubeDTO clubeDTOCasa){ return partidaService.getPartidasComClubeCasa(clubeDTOCasa); }

    @GetMapping("/partidas-por-clube-visitante")
    public List<Partida> getPartidasComClubeVisitante(@RequestBody ClubeDTO clubeDTOVisitante){ return partidaService.getPartidasComClubeVisitante(clubeDTOVisitante); }

    @GetMapping("/retrospectiva-clube-casa")
    public RestrospectivaDTO getRetrospectivaGeralClubeCasa(@RequestBody ClubeDTO clubeDTO){
        return partidaService.getRetrospectivaGeralClubeCasa(clubeDTO);
    }

    @GetMapping("/retrospectiva-clube-visitante")
    public RestrospectivaDTO getRetrospectivaGeralClubeVisitante(@RequestBody ClubeDTO clubeDTO){
        return partidaService.getRetrospectivaGeralClubeVisitante(clubeDTO);
    }

    @GetMapping("/retrospectiva-clube-geral")
    public RestrospectivaDTO getRetrospectivaGeralClube(@RequestBody ClubeDTO clubeDTO){
        return partidaService.getRetrospectivaGeralClube(clubeDTO);
    }

    @GetMapping("/retrospectiva-confronto")
    public RetrospectivaPorConfrontoDTO getRetrospectivaConfronto(@RequestBody ConfrontoDTO confrontoDTO){
        return partidaService.getRetrospectivaConfronto(confrontoDTO);
    }

    @GetMapping("/lista-fregueses")
    public List<ListaDeFreguesesDTO> getListaDeFregueses(@RequestBody ClubeDTO clubeDTO){
        return partidaService.getTop5ListaDeFregueses(clubeDTO);
    }

    @PutMapping("/{id}")
    public String updatePartida(@PathVariable Long id, @Valid @RequestBody PartidaDTO partidaDTO){ return partidaService.updatePartida(id, partidaDTO); }

    @DeleteMapping("/{id}")
    public void deletePartida(@PathVariable Long id){
        partidaService.deletePartida(id);
    }
}