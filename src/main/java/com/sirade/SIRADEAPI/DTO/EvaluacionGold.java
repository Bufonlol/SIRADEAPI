package com.sirade.SIRADEAPI.DTO;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "evaluaciones_gold")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EvaluacionGold {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int fev1Percent;
    private int catScore;
    private int exacerbacionesLeves;
    private int exacerbacionesGraves;

    private String goldGrado;
    private String grupo;

    @OneToOne
    @JoinColumn(name = "usuario_id", unique = true)
    private UsuarioDTO usuario;
}
