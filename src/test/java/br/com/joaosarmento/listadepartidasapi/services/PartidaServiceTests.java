package br.com.joaosarmento.listadepartidasapi.services;


import br.com.joaosarmento.listadepartidasapi.DTOs.PartidaDTO;
import br.com.joaosarmento.listadepartidasapi.models.Partida;
import br.com.joaosarmento.listadepartidasapi.repositories.PartidaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PartidaServiceTests {

    @Mock
    private PartidaRepository partidaRepository;
    @Spy
    private ModelMapper modelMapper = new ModelMapper();
    @InjectMocks
    private PartidaService partidaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPostWhenOk(){
        LocalDateTime data = LocalDateTime.parse("2013-11-30T10:00:00");

        when(partidaRepository.findByEstadioDaPartida("Frasqueirão")).thenReturn(Collections.emptyList());
        when(partidaRepository.checkClubeporDia(data, "Flamengo", "Botafogo")).thenReturn(false);

        when(partidaRepository.save(any(Partida.class))).thenAnswer(invocationOnMock -> {
            Partida partida = invocationOnMock.getArgument(0);
            partida.setId(1);
            return partida;
        });

        PartidaDTO input = new PartidaDTO("Flamengo", "Botafogo", 1, 2, data, "Frasqueirão");

        Partida partida = partidaService.postPartida(input);

        assertEquals(1, partida.getId());
        assertEquals("Flamengo", partida.getClubeCasa());
        assertEquals("Botafogo", partida.getClubeVisitante());
        assertEquals(1, partida.getGolsTimeCasa());
        assertEquals(2, partida.getGolsTimeVisitante());
        assertEquals(data, partida.getDataDaPartida());
        assertEquals("Frasqueirão", partida.getEstadioDaPartida());

        verify(partidaRepository).findByEstadioDaPartida("Frasqueirão");
        verify(partidaRepository).checkClubeporDia(data, "Flamengo", "Botafogo");
        verify(partidaRepository).save(any(Partida.class));
    }

    @Test
    public void testPostWhenTimeInvalid(){
        PartidaDTO input = new PartidaDTO("Flamengo", "Botafogo", 1, 2, LocalDateTime.parse("2013-11-30T07:00:00"), "Frasqueirão");

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> partidaService.postPartida(input)
        );

        assertEquals(HttpStatus.PRECONDITION_FAILED, exception.getStatusCode());
        assertEquals("Horário Inválido!", exception.getReason());
    }

    @Test
    public void testPostWhenEstadioInvalid(){
        Partida index0 = new Partida(1L,"Flamengo", "Botafogo", 1, 2, LocalDateTime.parse("2013-11-30T10:00:00"), "Frasqueirão");
        Partida index1 = new Partida(2L,"América", "Atlético", 5, 4, LocalDateTime.parse("2013-12-30T10:00:00"), "Frasqueirão");

        when(partidaRepository.findByEstadioDaPartida("Frasqueirão")).thenReturn(Arrays.asList(index0, index1));

        PartidaDTO input = new PartidaDTO("Botafogo", "Palmeiras", 5, 2, LocalDateTime.parse("2013-11-30T13:00:00"), "Frasqueirão");

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> partidaService.postPartida(input)
        );

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals("Jogo já existente para essa data nesse estádio!", exception.getReason());

        verify(partidaRepository).findByEstadioDaPartida("Frasqueirão");
        verify(partidaRepository, never()).checkClubeporDia(any(), anyString(), anyString());
        verify(partidaRepository, never()).save(any(Partida.class));
    }

    @Test
    public void testPostWhenIntervaloDeTempoDoClubeInvalido(){
        when(partidaRepository.findByEstadioDaPartida("Frasqueirão")).thenReturn(Collections.emptyList());
        when(partidaRepository.checkClubeporDia(LocalDateTime.parse("2013-11-30T10:00:00"), "Flamengo", "Botafogo")).thenReturn(true);

        PartidaDTO input = new PartidaDTO("Flamengo", "Botafogo", 1, 2, LocalDateTime.parse("2013-11-30T10:00:00"), "Frasqueirão");

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> partidaService.postPartida(input)
        );

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals("Um dos clubes ja tem um jogo com menos de 2 dias de diferença!", exception.getReason());

        verify(partidaRepository).findByEstadioDaPartida("Frasqueirão");
        verify(partidaRepository).checkClubeporDia(LocalDateTime.parse("2013-11-30T10:00:00"), "Flamengo", "Botafogo");
        verify(partidaRepository, never()).save(any(Partida.class));
    }

    @Test
    public void testGetAllWhenRepositoryIsEmpty() {
        when(partidaRepository.findAll()).thenReturn(Collections.emptyList());

        List<Partida> listaDePartidas = partidaService.getAll();

        assertTrue(listaDePartidas.isEmpty());
        verify(partidaRepository).findAll();
    }

    @Test
    public void testGetAllWhenRepositoryIsFull() {
        Partida index0 = new Partida(1L,"Flamengo", "Botafogo", 1, 2, LocalDateTime.parse("2013-11-30T10:00:00"), "Frasqueirão");

        when(partidaRepository.findAll()).thenReturn(List.of(index0));

        List<Partida> listaDePartidas = partidaService.getAll();

        Partida partida = listaDePartidas.get(0);
        assertEquals(1, partida.getId());
        assertEquals("Flamengo", partida.getClubeCasa());
        assertEquals("Botafogo", partida.getClubeVisitante());
        assertEquals(1, partida.getGolsTimeCasa());
        assertEquals(2, partida.getGolsTimeVisitante());
        assertEquals(LocalDateTime.parse("2013-11-30T10:00:00"), partida.getDataDaPartida());
        assertEquals("Frasqueirão", partida.getEstadioDaPartida());

        verify(partidaRepository).findAll();
    }

    @Test
    public void testGetByIdWhenOk(){
        when(partidaRepository.findById(1L)).thenReturn(Optional.of(new Partida(1L,"Flamengo", "Botafogo", 1, 2, LocalDateTime.parse("2013-11-30T10:00:00"), "Frasqueirão")));

        Partida partida = partidaService.getPartidaById(1L);

        assertEquals("Flamengo", partida.getClubeCasa());
        assertEquals("Botafogo", partida.getClubeVisitante());
        assertEquals(1, partida.getGolsTimeCasa());
        assertEquals(2, partida.getGolsTimeVisitante());
        assertEquals(LocalDateTime.parse("2013-11-30T10:00:00"), partida.getDataDaPartida());
        assertEquals("Frasqueirão", partida.getEstadioDaPartida());

        verify(partidaRepository).findById(1L);
    }

    @Test
    public void testGetByIdWhenIdNotExists(){
        when(partidaRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> partidaService.getPartidaById(1L)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Id inexistente!", exception.getReason());

        verify(partidaRepository).findById(1L);
    }

    @Test
    public void testPutWhenOk(){
        when(partidaRepository.findByEstadioDaPartida("Frasqueirão")).thenReturn(Collections.emptyList());
        when(partidaRepository.checkClubeporDia(LocalDateTime.parse("2013-11-30T10:00:00"), "Flamengo", "Botafogo")).thenReturn(false);
        when(partidaRepository.findById(1L)).thenReturn(Optional.of(new Partida(1L,"Flamengo", "Botafogo", 1, 2, LocalDateTime.parse("2013-11-30T10:00:00"), "Frasqueirão")));

        when(partidaRepository.save(any(Partida.class))).thenAnswer(invocationOnMock -> {
            Partida partida = invocationOnMock.getArgument(0);
            partida.setId(1);
            return partida;
        });

        PartidaDTO input = new PartidaDTO("Flamengo", "Botafogo", 1, 2, LocalDateTime.parse("2013-11-30T10:00:00"), "Frasqueirão");
        PartidaDTO partidaDTO = partidaService.updatePartida(1L, input);

        assertEquals("Flamengo", partidaDTO.getClubeCasa());
        assertEquals("Botafogo", partidaDTO.getClubeVisitante());
        assertEquals(1, partidaDTO.getGolsTimeCasa());
        assertEquals(2, partidaDTO.getGolsTimeVisitante());
        assertEquals(LocalDateTime.parse("2013-11-30T10:00:00"), partidaDTO.getDataDaPartida());
        assertEquals("Frasqueirão", partidaDTO.getEstadioDaPartida());

        verify(partidaRepository).findByEstadioDaPartida("Frasqueirão");
        verify(partidaRepository).checkClubeporDia(LocalDateTime.parse("2013-11-30T10:00:00"), "Flamengo", "Botafogo");
        verify(partidaRepository).findById(1L);
        verify(partidaRepository).save(any(Partida.class));
    }

    @Test
    public void testPutWhenTimeInvalid(){
        PartidaDTO input = new PartidaDTO("Flamengo", "Botafogo", 1, 2, LocalDateTime.parse("2013-11-30T07:00:00"), "Frasqueirão");

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> partidaService.updatePartida(1L, input)
        );

        assertEquals(HttpStatus.PRECONDITION_FAILED, exception.getStatusCode());
        assertEquals("Horário Inválido!", exception.getReason());
    }

    @Test
    public void testPutWhenEstadioInvalid(){
        Partida index0 = new Partida(1L,"Flamengo", "Botafogo", 1, 2, LocalDateTime.parse("2013-11-30T10:00:00"), "Frasqueirão");

        when(partidaRepository.findByEstadioDaPartida("Frasqueirão")).thenReturn(List.of(index0));

        PartidaDTO input = new PartidaDTO("Botafogo", "Palmeiras", 5, 2, LocalDateTime.parse("2013-11-30T13:00:00"), "Frasqueirão");

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> partidaService.updatePartida(1L, input)
        );

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals("Jogo já existente para essa data nesse estádio!", exception.getReason());

        verify(partidaRepository).findByEstadioDaPartida("Frasqueirão");
        verify(partidaRepository, never()).checkClubeporDia(any(), anyString(), anyString());
        verify(partidaRepository, never()).save(any(Partida.class));
    }

    @Test
    public void testPutWhenIntervaloDeTempoDoClubeInvalido(){
        when(partidaRepository.findByEstadioDaPartida("Frasqueirão")).thenReturn(Collections.emptyList());
        when(partidaRepository.checkClubeporDia(LocalDateTime.parse("2013-11-30T10:00:00"), "Flamengo", "Botafogo")).thenReturn(true);

        PartidaDTO input = new PartidaDTO("Flamengo", "Botafogo", 1, 2, LocalDateTime.parse("2013-11-30T10:00:00"), "Frasqueirão");

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> partidaService.updatePartida(1L, input)
        );

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals("Um dos clubes ja tem um jogo com menos de 2 dias de diferença!", exception.getReason());

        verify(partidaRepository).findByEstadioDaPartida("Frasqueirão");
        verify(partidaRepository).checkClubeporDia(LocalDateTime.parse("2013-11-30T10:00:00"), "Flamengo", "Botafogo");
        verify(partidaRepository, never()).save(any(Partida.class));
    }

    @Test
    public void testPutWhenIdNotExists(){
        when(partidaRepository.findByEstadioDaPartida("Frasqueirão")).thenReturn(Collections.emptyList());
        when(partidaRepository.checkClubeporDia(LocalDateTime.parse("2013-11-30T10:00:00"), "Flamengo", "Botafogo")).thenReturn(false);
        when(partidaRepository.findById(1L)).thenReturn(Optional.empty());

        PartidaDTO input = new PartidaDTO("Flamengo", "Botafogo", 1, 2, LocalDateTime.parse("2013-11-30T10:00:00"), "Frasqueirão");

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> partidaService.updatePartida(1L, input)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Id inexistente!", exception.getReason());

        verify(partidaRepository).findByEstadioDaPartida("Frasqueirão");
        verify(partidaRepository).checkClubeporDia(LocalDateTime.parse("2013-11-30T10:00:00"), "Flamengo", "Botafogo");
        verify(partidaRepository).findById(1L);
        verify(partidaRepository, never()).save(any(Partida.class));
    }

    @Test
    public void testDeleteWhenOk(){
        when(partidaRepository.findById(1L)).thenReturn(Optional.of(new Partida(1L,"Flamengo", "Botafogo", 1, 2, LocalDateTime.parse("2013-11-30T10:00:00"), "Frasqueirão")));

        partidaService.deletePartida(1L);

        verify(partidaRepository).findById(1L);
        verify(partidaRepository).deleteById(1L);
    }

    @Test
    public void testDeleteWhenIdNotExists(){
        when(partidaRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> partidaService.deletePartida(1L)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Id inexistente!", exception.getReason());

        verify(partidaRepository, never()).deleteById(anyLong());
    }
}
