package br.com.joaosarmento.listadepartidasapi.repositories;

import br.com.joaosarmento.listadepartidasapi.models.Partida;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartidaRepository extends JpaRepository <Partida, Long>{

}