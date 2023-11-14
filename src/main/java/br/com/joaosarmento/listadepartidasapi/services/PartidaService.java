package br.com.joaosarmento.listadepartidasapi.services;

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
        return partidaRepository.checkClubeporDia(dataDaPartida,clube);
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

        if ( valido != "Validado") return valido;

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

    public RetrospectivaDTO getRetrospectivaGeralClubeCasa(ClubeDTO clubeDTOCasa){
        List <Partida> partidasClubeCasa= getPartidasComClubeCasa(clubeDTOCasa);
        Long vitoriasCasa = partidasClubeCasa.stream().filter(partida -> partida.getGolsTimeCasa() > partida.getGolsTimeVisitante()).count();
        Long derrotasCasa = partidasClubeCasa.stream().filter(partida -> partida.getGolsTimeCasa() < partida.getGolsTimeVisitante()).count();
        Long empatesCasa = partidasClubeCasa.stream().filter(partida -> partida.getGolsTimeCasa() == partida.getGolsTimeVisitante()).count();
        int golsProCasa = partidasClubeCasa.stream().mapToInt(Partida::getGolsTimeCasa).reduce(0, Integer::sum);
        int golsContraCasa = partidasClubeCasa.stream().mapToInt(Partida::getGolsTimeVisitante).reduce(0, Integer::sum);

        return new RetrospectivaDTO(vitoriasCasa, derrotasCasa, empatesCasa, golsProCasa, golsContraCasa);
    }

    public RetrospectivaDTO getRetrospectivaGeralClubeVisitante(ClubeDTO clubeDTOCasa){
        List <Partida> partidasClubeVisitante= getPartidasComClubeVisitante(clubeDTOCasa);
        Long vitoriasVisitante = partidasClubeVisitante.stream().filter(partida -> partida.getGolsTimeVisitante() > partida.getGolsTimeCasa()).count();
        Long derrotasVisitante = partidasClubeVisitante.stream().filter(partida -> partida.getGolsTimeVisitante() < partida.getGolsTimeCasa()).count();
        Long empatesVisitante = partidasClubeVisitante.stream().filter(partida ->  partida.getGolsTimeVisitante() == partida.getGolsTimeCasa()).count();
        int golsProVisitante= partidasClubeVisitante.stream().mapToInt(Partida::getGolsTimeVisitante).reduce(0, Integer::sum);
        int golsContraVisitante = partidasClubeVisitante.stream().mapToInt(Partida::getGolsTimeCasa).reduce(0, Integer::sum);

        return new RetrospectivaDTO(vitoriasVisitante, derrotasVisitante, empatesVisitante, golsProVisitante, golsContraVisitante);
    }

    public String updatePartida(Long id, PartidaDTO partidaDTO){
        String valido = postAndPutValidations(partidaDTO);

        if(!partidaRepository.existsById(id)) return "Partida não encontrado!";
        if ( valido != "Validado") return valido;

        Partida partida = modelMapper.map(partidaDTO, Partida.class);
        partida.setId(id);
        partidaRepository.save(partida);

        return "Atualizado!";
    }

    public void deletePartida(Long id){
        partidaRepository.deleteById(id);
    }


}
