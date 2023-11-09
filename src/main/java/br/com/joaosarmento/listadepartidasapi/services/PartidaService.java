package br.com.joaosarmento.listadepartidasapi.services;

import br.com.joaosarmento.listadepartidasapi.DTOs.PartidaDTO;
import br.com.joaosarmento.listadepartidasapi.DTOs.ClubeDTO;
import br.com.joaosarmento.listadepartidasapi.DTOs.EstadioDTO;
import br.com.joaosarmento.listadepartidasapi.models.Partida;
import br.com.joaosarmento.listadepartidasapi.DTOs.UpdateFormDTO;
import br.com.joaosarmento.listadepartidasapi.repositories.PartidaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartidaService {

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public void postPartida(PartidaDTO partidaDto){
        partidaRepository.save(modelMapper.map(partidaDto, Partida.class));
    }

    public List<Partida> getTodasAsPartidas(){
        return partidaRepository.findAll();
    }

    public Optional<Partida> getUmaPartida(Long id){
        return partidaRepository.findById(id);
    }

    public List<Partida> getPartidaPorEstadio(EstadioDTO estadioDaPartida){
        return partidaRepository.findByEstadioDaPartida(estadioDaPartida.getEstadioDaPartida());
    }

    public List<Partida> getPartidasComGoleadas(){
        return getTodasAsPartidas()
                .stream()
                .filter(partida -> {
                    int diferencaDeGols = partida.getGolsTimeCasa() - partida.getGolsTimeVisitante();
                    return diferencaDeGols>=3||diferencaDeGols<=-3;
                })
                .toList();
    }

    public List<Partida> getPartidasSemGols(){
        return getTodasAsPartidas()
                .stream()
                .filter(partida -> partida.getGolsTimeCasa()==0&&partida.getGolsTimeVisitante()==0)
                .toList();
    }

    public List<Partida> getPartidasComClube(ClubeDTO clubeDTO){
        return partidaRepository.findByClubeCasaOrClubeVisitante(clubeDTO.getClube(), clubeDTO.getClube());
    }

    public List<Partida> getPartidasComClubeCasa(ClubeDTO clubeDTOCasa){
        return partidaRepository.findByClubeCasa(clubeDTOCasa.getClube());
    }

    public List<Partida> getPartidasComClubeVisitante(ClubeDTO clubeDTOVisitante){
        return partidaRepository.findByClubeVisitante(clubeDTOVisitante.getClube());
    }

    public String updatePartida(Long id, UpdateFormDTO form){
        if(!partidaRepository.existsById(id)){return "Partida não encontrado!";}

        Partida partida = modelMapper.map(form,Partida.class);
        partida.setId(id);
        partidaRepository.save(partida);

        return "Atualizado!";
    }

    public void deletePartida(Long id){
        partidaRepository.deleteById(id);
    }


}
