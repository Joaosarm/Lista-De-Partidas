package br.com.joaosarmento.listadepartidasapi.controllers;

import br.com.joaosarmento.listadepartidasapi.DTOs.PartidaDTO;
import br.com.joaosarmento.listadepartidasapi.models.Partida;
import br.com.joaosarmento.listadepartidasapi.models.UpdateForm;
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
    public Optional<Partida> getTodasAsPartidas(@PathVariable Long id){
        return partidaService.getUmaPartida(id);
    }

    @PutMapping("/{id}")
    public String updatePartida(@PathVariable Long id, @RequestBody UpdateForm form){
        return partidaService.updatePartida(id, form);
    }

    @DeleteMapping("/{id}")
    public void deletePartida(@PathVariable Long id){
        partidaService.deletePartida(id);
    }
}