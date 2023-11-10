package br.com.joaosarmento.listadepartidasapi.repositories;

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
    @Query("SELECT count(id) FROM Partida " +
            "WHERE (FUNCTION('TIMEDIFF', dataDaPartida, :dataNovaPartida) between -480000 and 480000)" +
            "AND (clubeCasa = :clubeNovo or clubeVisitante = :clubeNovo)")
    Integer checkClubeporDia(@Param("dataNovaPartida")LocalDateTime dataDaPartida,
                             @Param("clubeNovo") String clube);
}