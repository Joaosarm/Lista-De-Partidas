package br.com.joaosarmento.listadepartidasapi.services;

import br.com.joaosarmento.listadepartidasapi.DTOs.PartidaDTO;
import br.com.joaosarmento.listadepartidasapi.DTOs.ClubeDTO;
import br.com.joaosarmento.listadepartidasapi.models.Partida;
import br.com.joaosarmento.listadepartidasapi.DTOs.UpdateFormDTO;
import br.com.joaosarmento.listadepartidasapi.repositories.PartidaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class PartidaService {

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private ModelMapper modelMapper;

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
        if(partidaRepository.checkClubeporDia(dataDaPartida,clube) > 0) return true;
        return false;
    }

    public String postAndPutValidations(LocalDateTime dataDaPartida, String estadioDaPartida, String clubeCasa, String clubeVisitante){
        if(validateHorarioPartida(dataDaPartida))
            return "Horário Inválido!";
        if(validateEstadioNoDia(estadioDaPartida, dataDaPartida))
            return "Jogo já existente para essa data nesse estádio!";
        if(validateClubePorIntervaloDeTempo(dataDaPartida, clubeCasa))
            return "Clube da casa ja tem um jogo com menos de 2 dias de diferença!";
        if(validateClubePorIntervaloDeTempo(dataDaPartida, clubeVisitante))
            return "Clube visitante ja tem um jogo com menos de 2 dias de diferença!";

        return "Validado";
    }

    public String postPartida(PartidaDTO partidaDto){
        String valido = postAndPutValidations(partidaDto.getDataDaPartida(), partidaDto.getEstadioDaPartida(),
                partidaDto.getClubeCasa(), partidaDto.getClubeVisitante());
        if ( valido != "Validado") return valido;

        partidaRepository.save(modelMapper.map(partidaDto, Partida.class));
        return "Partida Inserida! ";
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

    public String updatePartida(Long id, UpdateFormDTO form){
        String valido = postAndPutValidations(form.getDataDaPartida(), form.getEstadioDaPartida(),
                form.getClubeCasa(), form.getClubeVisitante());

        if(!partidaRepository.existsById(id)) return "Partida não encontrado!";
        if ( valido != "Validado") return valido;

        Partida partida = modelMapper.map(form,Partida.class);
        partida.setId(id);
        partidaRepository.save(partida);

        return "Atualizado!";
    }

    public void deletePartida(Long id){
        partidaRepository.deleteById(id);
    }


}
