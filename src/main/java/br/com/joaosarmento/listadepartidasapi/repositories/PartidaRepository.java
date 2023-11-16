package br.com.joaosarmento.listadepartidasapi.repositories;

import br.com.joaosarmento.listadepartidasapi.DTOs.Retrospectiva;
import br.com.joaosarmento.listadepartidasapi.models.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PartidaRepository extends JpaRepository <Partida, Long>{
    List<Partida> findByEstadioDaPartida(String estadioDaPartida);
    List<Partida> findByClubeCasa(String clubeCasa);
    List<Partida> findByClubeVisitante(String clubeVisitante);
    List<Partida> findByClubeCasaOrClubeVisitante(String clubeCasa, String clubeVisitante);
    @Query("SELECT count(id) > 0 FROM Partida " +
            "WHERE (FUNCTION('TIMEDIFF', dataDaPartida, :dataNovaPartida) between -480000 and 480000)" +
            "AND (clubeCasa = :clubeNovo or clubeVisitante = :clubeNovo)")
    Boolean checkClubeporDia(@Param("dataNovaPartida")LocalDateTime dataDaPartida,
                             @Param("clubeNovo") String clube);
    @Query("SELECT FUNCTION('SUM', (golsTimeCasa > golsTimeVisitante)) as vitorias, " +
            "FUNCTION('SUM', (golsTimeCasa = golsTimeVisitante)) as empates," +
            "FUNCTION('SUM', (golsTimeCasa < golsTimeVisitante)) as derrotas," +
            "FUNCTION('SUM', (golsTimeCasa)) as golsPro, FUNCTION('SUM', (golsTimeVisitante)) as golsContra " +
            "FROM Partida " +
            "WHERE clubeCasa = :clubeCasa ")
    Retrospectiva getRetrospectivaClubeCasa(@Param("clubeCasa")String clubeCasa);
    @Query("SELECT FUNCTION('SUM', (golsTimeVisitante > golsTimeCasa)) as vitorias, " +
            "FUNCTION('SUM', (golsTimeVisitante = golsTimeCasa)) as empates," +
            "FUNCTION('SUM', (golsTimeVisitante < golsTimeCasa)) as derrotas," +
            "FUNCTION('SUM', (golsTimeVisitante)) as golsPro, FUNCTION('SUM', (golsTimeCasa)) as golsContra " +
            "FROM Partida " +
            "WHERE clubeVisitante = :clubeVisitante ")
    Retrospectiva getRetrospectivaClubeVisitante(@Param("clubeVisitante")String clubeVisitante);
    @Query("SELECT FUNCTION('SUM', (golsTimeCasa > golsTimeVisitante)) as vitorias, " +
            "FUNCTION('SUM', (golsTimeCasa = golsTimeVisitante)) as empates," +
            "FUNCTION('SUM', (golsTimeCasa < golsTimeVisitante)) as derrotas," +
            "FUNCTION('SUM', (golsTimeCasa)) as golsPro, FUNCTION('SUM', (golsTimeVisitante)) as golsContra " +
            "FROM Partida " +
            "WHERE clubeCasa = :primeiroClube AND clubeVisitante = :segundoClube ")
    Retrospectiva getRetrospectivaConfronto(@Param("primeiroClube")String primeiroClube, @Param("segundoClube")String segundoClube);
}