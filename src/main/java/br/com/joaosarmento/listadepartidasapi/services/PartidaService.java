package br.com.joaosarmento.listadepartidasapi.services;

import br.com.joaosarmento.listadepartidasapi.DTOs.PartidaDTO;
import br.com.joaosarmento.listadepartidasapi.models.Partida;
import br.com.joaosarmento.listadepartidasapi.models.UpdateForm;
import br.com.joaosarmento.listadepartidasapi.repositories.PartidaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<Partida> getTodasAsPartidas(){
        return partidaRepository.findAll();
    }

    public Optional<Partida> getUmaPartida(Long id){
        return partidaRepository.findById(id);
    }

    public String updatePartida(Long id, UpdateForm form){

        try {
            Partida partida = partidaRepository.getReferenceById(id);

            if(partida.getClubeCasa() != null) {
                partida.setClubeCasa(form.getClubeCasa());
                partida.setClubeVisitante(form.getClubeVisitante());
                partida.setGolsTimeCasa(form.getGolsTimeCasa());
                partida.setGolsTimeVisitante(form.getGolsTimeVisitante());
                partida.setDataDaPartida(form.getDataDaPartida());
                partida.setEstadioDaPartida(form.getEstadioDaPartida());

                partidaRepository.save(partida);
            }
        } catch(EntityNotFoundException e){
            return "Partida não encontrado!";
        }

        return "Atualizado!";
    }

    public void deletePartida(Long id){
        partidaRepository.deleteById(id);
    }


}
