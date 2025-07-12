package com.sirade.SIRADEAPI.DTO;

import lombok.Data;

@Data
public class MedicamentoDTO {
    private String nombre;
    private String dosis;
    private String frecuencia;
    private String notas;
    private String hora; // formato HH:mm
    private Boolean recordatorio;
    private Long usuarioId;
}
