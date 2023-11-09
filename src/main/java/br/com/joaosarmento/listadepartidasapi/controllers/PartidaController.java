package br.com.joaosarmento.listadepartidasapi.controllers;

import br.com.joaosarmento.listadepartidasapi.DTOs.PartidaDTO;
import br.com.joaosarmento.listadepartidasapi.DTOs.ClubeDTO;
import br.com.joaosarmento.listadepartidasapi.DTOs.EstadioDTO;
import br.com.joaosarmento.listadepartidasapi.models.Partida;
import br.com.joaosarmento.listadepartidasapi.DTOs.UpdateFormDTO;
import br.com.joaosarmento.listadepartidasapi.services.PartidaService;
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
    public void postPartida(@RequestBody PartidaDTO partidaDTO){
        partidaService.postPartida(partidaDTO);
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
    public List<Partida> getPartidaPorEstadio(@RequestBody EstadioDTO estadioDaPartida){ return partidaService.getPartidaPorEstadio(estadioDaPartida); }

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

    @PutMapping("/{id}")
    public String updatePartida(@PathVariable Long id, @RequestBody UpdateFormDTO form){ return partidaService.updatePartida(id, form); }

    @DeleteMapping("/{id}")
    public void deletePartida(@PathVariable Long id){
        partidaService.deletePartida(id);
    }
}