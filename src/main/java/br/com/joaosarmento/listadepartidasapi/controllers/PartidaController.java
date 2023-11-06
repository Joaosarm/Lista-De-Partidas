package br.com.joaosarmento.listadepartidasapi.controllers;

import br.com.joaosarmento.listadepartidasapi.DTOs.PartidaDTO;
import br.com.joaosarmento.listadepartidasapi.services.PartidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public String getText(){return "Hello World";}
}
