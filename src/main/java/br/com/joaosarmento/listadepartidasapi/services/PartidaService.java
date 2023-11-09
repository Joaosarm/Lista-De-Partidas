package br.com.joaosarmento.listadepartidasapi.services;

import br.com.joaosarmento.listadepartidasapi.DTOs.PartidaDTO;
import br.com.joaosarmento.listadepartidasapi.DTOs.ClubeDTO;
import br.com.joaosarmento.listadepartidasapi.DTOs.EstadioDTO;
import br.com.joaosarmento.listadepartidasapi.models.Partida;
import br.com.joaosarmento.listadepartidasapi.DTOs.UpdateFormDTO;
import br.com.joaosarmento.listadepartidasapi.repositories.PartidaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
        List<Partida> partidas = getTodasAsPartidas();
        List<Partida> partidasComGoleada = new ArrayList<>();

        for(int i=0; i<partidas.size(); i++){
            int diferencaDeGols = partidas.get(i).getGolsTimeCasa() - partidas.get(i).getGolsTimeVisitante();
            if(diferencaDeGols>=3||diferencaDeGols<=-3) partidasComGoleada.add(partidas.get(i));
        }

        return partidasComGoleada;
    }

    public List<Partida> getPartidasSemGols(){
        List<Partida> partidas = getTodasAsPartidas();
        List<Partida> partidasSemGols = new ArrayList<>();

        for(int i=0; i<partidas.size(); i++){
            if(partidas.get(i).getGolsTimeCasa()==0&&partidas.get(i).getGolsTimeVisitante()==0) partidasSemGols.add(partidas.get(i));
        }

        return partidasSemGols;
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
