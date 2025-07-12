package com.sirade.SIRADEAPI.DTO;

import com.sirade.SIRADEAPI.DTO.UsuarioDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalTime;

@Data
@Entity
@Table(name = "medicamentos")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @NotBlank
    private String dosis;

    @NotBlank
    private String frecuencia;

    @Column(nullable = false)
    private LocalTime hora;

    private String notas;

    private Boolean recordatorio;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioDTO usuario;
}
