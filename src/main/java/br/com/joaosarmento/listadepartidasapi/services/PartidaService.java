package br.com.joaosarmento.listadepartidasapi.services;

import br.com.joaosarmento.listadepartidasapi.DTOs.*;
import br.com.joaosarmento.listadepartidasapi.models.Partida;
import br.com.joaosarmento.listadepartidasapi.repositories.PartidaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class PartidaService {

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private ModelMapper modelMapper;


    private void validateHorarioPartida(LocalDateTime dataDaPartida){
        LocalTime horaMinima = LocalTime.parse( "08:00:00" );
        LocalTime horaMaxima = LocalTime.parse( "22:00:00" );
        LocalTime horaDaPartida = dataDaPartida.toLocalTime();

        if(horaDaPartida.isBefore(horaMinima) || horaDaPartida.isAfter(horaMaxima))
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Horário Inválido!");
    }

    private void validateEstadioNoDia(String nomeDoEstadio, LocalDateTime DataDaPartida){
        boolean anyPartidaNoEstadioNoDia = getPartidaPorEstadio(nomeDoEstadio).stream().anyMatch(partida ->
            partida.getDataDaPartida().toLocalDate().isEqual(DataDaPartida.toLocalDate())
        );
        if(anyPartidaNoEstadioNoDia)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Jogo já existente para essa data nesse estádio!");
    }

    private void validateClubePorIntervaloDeTempo(PartidaDTO partidaDTO){
        if (partidaRepository.checkClubeporDia(partidaDTO.getDataDaPartida(),partidaDTO.getClubeCasa(),partidaDTO.getClubeVisitante()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Um dos clubes ja tem um jogo com menos de 2 dias de diferença!");
    }

    private RestrospectivaDTO checkIfRestrospectivaIsNull(Retrospectiva retrospectiva){
        if(retrospectiva.getVitorias() == null) return new RestrospectivaDTO();
        return new RestrospectivaDTO(retrospectiva);
    }

    private void postAndPutValidations(PartidaDTO partidaDTO){
        validateHorarioPartida(partidaDTO.getDataDaPartida());
        validateEstadioNoDia(partidaDTO.getEstadioDaPartida(), partidaDTO.getDataDaPartida());
        validateClubePorIntervaloDeTempo(partidaDTO);
    }

    public Partida postPartida(PartidaDTO partidaDto){
        postAndPutValidations(partidaDto);

        Partida partida = modelMapper.map(partidaDto, Partida.class);
        partidaRepository.save(partida);
        return partida;
    }

    public List<Partida> getAll(){
        return partidaRepository.findAll();
    }

    public Partida getPartidaById(Long id){
        return partidaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id inexistente!"));
    }

    public List<Partida> getPartidaPorEstadio(String nomeDoEstadio){
        return partidaRepository.findByEstadioDaPartida(nomeDoEstadio);
    }

    public List<Partida> getPartidasComGoleadas(){
        return getAll()
                .stream()
                .filter(partida -> {
                    int diferencaDeGols = partida.getGolsTimeCasa() - partida.getGolsTimeVisitante();
                    return diferencaDeGols>=3 || diferencaDeGols<=-3;
                })
                .toList();
    }

    public List<Partida> getPartidasSemGols(){
        return getAll()
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

    public RestrospectivaDTO getRetrospectivaGeralClubeCasa(ClubeDTO clubeDTOCasa){
        return checkIfRestrospectivaIsNull( partidaRepository.getRetrospectivaClubeCasa(clubeDTOCasa.getClube()) );
    }

    public RestrospectivaDTO getRetrospectivaGeralClubeVisitante(ClubeDTO clubeDTOVisitante){
        return checkIfRestrospectivaIsNull( partidaRepository.getRetrospectivaClubeVisitante(clubeDTOVisitante.getClube()) );
    }

    public RestrospectivaDTO getRetrospectivaGeralClube(ClubeDTO clubeDTOCasa){
        RestrospectivaDTO retrospectivaComoCasa = getRetrospectivaGeralClubeCasa(clubeDTOCasa);
        RestrospectivaDTO retrospectivaComoVisitante = getRetrospectivaGeralClubeVisitante(clubeDTOCasa);

        return new RestrospectivaDTO().mergeRetrospectivasPorClube(retrospectivaComoCasa, retrospectivaComoVisitante);
    }

    public RetrospectivaPorConfrontoDTO getRetrospectivaConfronto(ConfrontoDTO confrontoDTO) {
        RestrospectivaDTO retrospectivaPrimeiroClubeComoCasa = checkIfRestrospectivaIsNull(
                partidaRepository.getRetrospectivaConfronto(confrontoDTO.getPrimeiroClube(), confrontoDTO.getSegundoClube()));
        RestrospectivaDTO retrospectivaSegundoClubeComoCasa = checkIfRestrospectivaIsNull(
                partidaRepository.getRetrospectivaConfronto(confrontoDTO.getSegundoClube(), confrontoDTO.getPrimeiroClube()));

        return switch (confrontoDTO.getClubeMandante()) {
            case "1" -> new RetrospectivaPorConfrontoDTO().RetrospectivaPrimeiroClube(retrospectivaPrimeiroClubeComoCasa);
            case "2" -> new RetrospectivaPorConfrontoDTO().RetrospectivaSegundoClube(retrospectivaSegundoClubeComoCasa);
            case null, default -> new RetrospectivaPorConfrontoDTO().MergeRetrospectivasConfronto(retrospectivaPrimeiroClubeComoCasa, retrospectivaSegundoClubeComoCasa);
        };
    }

    public List<String> getNomeClubesAdversarios(ClubeDTO clubeDTO){
        List<String> clubesAdversarios = new ArrayList<>();
        String clubeASerChecado = clubeDTO.getClube();

        getPartidasComClube(clubeDTO).forEach(partida ->{
            String clubeCasa = partida.getClubeCasa();
            String clubeVisitante = partida.getClubeVisitante();

            if(!clubeCasa.equals(clubeASerChecado) && !clubesAdversarios.contains(clubeCasa)) clubesAdversarios.add(clubeCasa);
            else if(!clubeVisitante.equals(clubeASerChecado) && !clubesAdversarios.contains(clubeVisitante)) clubesAdversarios.add(clubeVisitante);
        });

        return clubesAdversarios;
    }

    public List<ListaDeFreguesesDTO> getListaDeFreguesesPositivos(ClubeDTO clubeDTO){
        List<String> clubesAdversarios = getNomeClubesAdversarios(clubeDTO);
        List<ListaDeFreguesesDTO> listaDeFregueses = new ArrayList<>();

        clubesAdversarios.forEach(clube ->{
            RetrospectivaPorConfrontoDTO retrospectivaAdversario = getRetrospectivaConfronto(new ConfrontoDTO(clubeDTO.getClube(), clube));
            int quantidadeVitorias = retrospectivaAdversario.getVitoriasPrimeiroTime();
            int quantidadeDerrotas = retrospectivaAdversario.getVitoriasSegundoTime();
            int quantidadeEmpates = retrospectivaAdversario.getEmpates();

            if(quantidadeVitorias > quantidadeDerrotas) listaDeFregueses.add(new ListaDeFreguesesDTO(clube, quantidadeVitorias, quantidadeDerrotas, quantidadeEmpates));
        });

        return listaDeFregueses;
    }

    public List<ListaDeFreguesesDTO> getTop5ListaDeFregueses(ClubeDTO clubeDTO){
        List<ListaDeFreguesesDTO> listaDeFregueses = getListaDeFreguesesPositivos(clubeDTO);
        listaDeFregueses.sort(new ListaDeFreguesesComparator());

        if(listaDeFregueses.size() > 5) return listaDeFregueses.subList(0,5);
        return listaDeFregueses;
    }

    public PartidaDTO updatePartida(Long id, PartidaDTO partidaDTO){
        postAndPutValidations(partidaDTO);
        Partida partida = getPartidaById(id);

        modelMapper.map(partidaDTO, partida);
        partidaRepository.save(partida);

        return modelMapper.map(partida, PartidaDTO.class);
    }

    public void deletePartida(Long id){
        getPartidaById(id);
        partidaRepository.deleteById(id);
    }
}