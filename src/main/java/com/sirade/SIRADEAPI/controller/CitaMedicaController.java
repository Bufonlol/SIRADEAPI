package com.sirade.SIRADEAPI.controller;

import com.sirade.SIRADEAPI.DTO.CitaMedica;
import com.sirade.SIRADEAPI.DTO.CrearCitaDTO;
import com.sirade.SIRADEAPI.DTO.UsuarioDTO;
import com.sirade.SIRADEAPI.repository.UsuarioRepository;
import com.sirade.SIRADEAPI.service.CitaMedicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaMedicaController {

    @Autowired
    private CitaMedicaService citaService;


    @PostMapping("/agendar/{pacienteId}")
    public CitaMedica agendar(@PathVariable Long pacienteId, @RequestBody CrearCitaDTO dto) {
        return citaService.agendarCita(pacienteId, dto);
    }

    @GetMapping("/paciente/{pacienteId}")
    public List<CitaMedica> obtenerCitasPaciente(@PathVariable Long pacienteId) {
        return citaService.obtenerCitasPorPaciente(pacienteId);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<CitaMedica> obtenerCitasDoctor(@PathVariable Long doctorId) {
        return citaService.obtenerCitasPorDoctor(doctorId);
    }


}
