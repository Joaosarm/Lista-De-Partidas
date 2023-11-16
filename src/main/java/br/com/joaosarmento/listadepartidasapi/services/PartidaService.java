package br.com.joaosarmento.listadepartidasapi.services;

import br.com.joaosarmento.listadepartidasapi.DTOs.ManipulateRestrospectivaDTO;
import br.com.joaosarmento.listadepartidasapi.DTOs.PartidaDTO;
import br.com.joaosarmento.listadepartidasapi.DTOs.ClubeDTO;
import br.com.joaosarmento.listadepartidasapi.DTOs.RetrospectivaDTO;
import br.com.joaosarmento.listadepartidasapi.models.Partida;
import br.com.joaosarmento.listadepartidasapi.repositories.PartidaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PartidaService {

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public boolean validarId(Long id){
        return !partidaRepository.existsById(id);
    }
    public boolean validateHorarioPartida(LocalDateTime dataDaPartida){
        LocalTime horaMinima = LocalTime.parse( "08:00:00" );
        LocalTime horaMaxima = LocalTime.parse( "22:00:00" );
        LocalTime horaDaPartida = dataDaPartida.toLocalTime();

        return horaDaPartida.isBefore(horaMinima) || horaDaPartida.isAfter(horaMaxima);
    }

    public boolean validateEstadioNoDia(String nomeDoEstadio, LocalDateTime DataDaPartida){
        return getPartidaPorEstadio(nomeDoEstadio).stream().anyMatch(partida ->
            partida.getDataDaPartida().toLocalDate().isEqual(DataDaPartida.toLocalDate())
        );
    }

    public boolean validateClubePorIntervaloDeTempo(LocalDateTime dataDaPartida, String clube){
        return partidaRepository.checkClubeporDia(dataDaPartida,clube);
    }

    public ManipulateRestrospectivaDTO checkIfNull(RetrospectivaDTO retrospectiva){
        if(retrospectiva.getVitorias() == null) return new ManipulateRestrospectivaDTO();
        return new ManipulateRestrospectivaDTO(retrospectiva);
    }

    public String postAndPutValidations(PartidaDTO partidaDTO){
        if(validateHorarioPartida(partidaDTO.getDataDaPartida()))
            return "Horário Inválido!";
        if(validateEstadioNoDia(partidaDTO.getEstadioDaPartida(), partidaDTO.getDataDaPartida()))
            return "Jogo já existente para essa data nesse estádio!";
        if(validateClubePorIntervaloDeTempo(partidaDTO.getDataDaPartida(), partidaDTO.getClubeCasa()))
            return "Clube da casa ja tem um jogo com menos de 2 dias de diferença!";
        if(validateClubePorIntervaloDeTempo(partidaDTO.getDataDaPartida(), partidaDTO.getClubeVisitante()))
            return "Clube visitante ja tem um jogo com menos de 2 dias de diferença!";

        return "Validado";
    }

    public String postPartida(PartidaDTO partidaDto){
        String valido = postAndPutValidations(partidaDto);

        if (!valido.equals("Validado")) return valido;

        partidaRepository.save(modelMapper.map(partidaDto, Partida.class));
        return "Partida Inserida!";
    }

    public List<Partida> getTodasAsPartidas(){
        return partidaRepository.findAll();
    }

    public Optional<Partida> getUmaPartida(Long id){
        return partidaRepository.findById(id);
    }

    public List<Partida> getPartidaPorEstadio(String nomeDoEstadio){
        return partidaRepository.findByEstadioDaPartida(nomeDoEstadio);
    }

    public List<Partida> getPartidasComGoleadas(){
        return getTodasAsPartidas()
                .stream()
                .filter(partida -> {
                    int diferencaDeGols = partida.getGolsTimeCasa() - partida.getGolsTimeVisitante();
                    return diferencaDeGols>=3 || diferencaDeGols<=-3;
                })
                .toList();
    }

    public List<Partida> getPartidasSemGols(){
        return getTodasAsPartidas()
                .stream()
                .filter(partida -> partida.getGolsTimeCasa()==0 && partida.getGolsTimeVisitante()==0)
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

    public ManipulateRestrospectivaDTO getRetrospectivaGeralClubeCasa(ClubeDTO clubeDTOCasa){
        return checkIfNull(partidaRepository.getRetrospectivaClubeCasa(clubeDTOCasa.getClube()));
    }

    public ManipulateRestrospectivaDTO getRetrospectivaGeralClubeVisitante(ClubeDTO clubeDTOVisitante){
        return checkIfNull(partidaRepository.getRetrospectivaClubeVisitante(clubeDTOVisitante.getClube()));
    }

    public ManipulateRestrospectivaDTO getRetrospectivaGeralClube(ClubeDTO clubeDTOCasa){
        ManipulateRestrospectivaDTO retrospectivaComoCasa = getRetrospectivaGeralClubeCasa(clubeDTOCasa);
        ManipulateRestrospectivaDTO retrospectivaComoVisitante = getRetrospectivaGeralClubeVisitante(clubeDTOCasa);

        return new ManipulateRestrospectivaDTO(retrospectivaComoCasa, retrospectivaComoVisitante);
    }

    public String updatePartida(Long id, PartidaDTO partidaDTO){
        String valido = postAndPutValidations(partidaDTO);

        if(validarId(id)) return "Partida não encontrado!";
        if (!valido.equals("Validado")) return valido;

        Partida partida = modelMapper.map(partidaDTO, Partida.class);
        partida.setId(id);
        partidaRepository.save(partida);

        return "Atualizado!";
    }

    public void deletePartida(Long id){
        partidaRepository.deleteById(id);
    }

}