package com.sirade.SIRADEAPI.DTO;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CrearCitaDTO {
    private Long doctorId;
    private LocalDate fecha;
    private LocalTime hora;
    private String tipoConsulta; // "PRESENCIAL" o "VIRTUAL"
    private String notas;
}
