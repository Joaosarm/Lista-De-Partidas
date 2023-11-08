package br.com.joaosarmento.listadepartidasapi.repositories;

import br.com.joaosarmento.listadepartidasapi.models.Partida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartidaRepository extends JpaRepository <Partida, Long>{
    List<Partida> findByEstadioDaPartida(String estadioDaPartida);
    List<Partida> findByClubeCasa(String clubeCasa);
    List<Partida> findByClubeVisitante(String clubeVisitante);
    List<Partida> findByClubeCasaOrClubeVisitante(String clubeCasa, String clubeVisitante);
}