package com.sirade.SIRADEAPI.DTO;

import com.sirade.SIRADEAPI.DTO.UsuarioDTO;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "citas_medicas")
public class CitaMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Paciente que agenda la cita
    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private UsuarioDTO paciente;

    // Doctor que atiende
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private UsuarioDTO doctor;

    private LocalDate fecha;
    private LocalTime hora;

    public enum TipoConsulta { PRESENCIAL, VIRTUAL }

    @Enumerated(EnumType.STRING)
    private TipoConsulta tipoConsulta;

    private String notas;
}
