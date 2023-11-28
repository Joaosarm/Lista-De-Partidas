package br.com.joaosarmento.listadepartidasapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "partida")
public class Partida {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @NotNull
    @Column
    private long id;
    @NotBlank
    @Column
    private String clubeCasa;
    @NotBlank
    @Column
    private String clubeVisitante;
    @NotNull
    @Column
    private int golsTimeCasa;
    @NotNull
    @Column
    private int golsTimeVisitante;
    @NotNull
    @Column
    private LocalDateTime dataDaPartida;
    @NotBlank
    @Column
    private String estadioDaPartida;
}
