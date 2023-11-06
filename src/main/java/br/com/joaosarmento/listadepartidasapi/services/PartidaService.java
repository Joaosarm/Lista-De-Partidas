package br.com.joaosarmento.listadepartidasapi.services;

import br.com.joaosarmento.listadepartidasapi.DTOs.PartidaDTO;
import br.com.joaosarmento.listadepartidasapi.models.Partida;
import br.com.joaosarmento.listadepartidasapi.repositories.PartidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartidaService {

    @Autowired
    private PartidaRepository partidaRepository;

    public void postPartida(PartidaDTO partidaDto){
        Partida partida = new Partida();
        partida.setId(partidaDto.getId());
        partida.setClubeCasa(partidaDto.getClubeCasa());
        partida.setClubeVisitante(partidaDto.getClubeVisitante());
        partida.setGolsTimeCasa(partidaDto.getGolsTimeCasa());
        partida.setGolsTimeVisitante(partidaDto.getGolsTimeVisitante());
        partida.setDataDaPartida(partidaDto.getDataDaPartida());
        partida.setEstadioDaPartida(partidaDto.getEstadioDaPartida());

        partidaRepository.save(partida);
    }
}
