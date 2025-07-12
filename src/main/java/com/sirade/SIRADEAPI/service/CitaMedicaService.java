package com.sirade.SIRADEAPI.service;

import com.sirade.SIRADEAPI.DTO.CitaMedica;
import com.sirade.SIRADEAPI.DTO.CrearCitaDTO;
import com.sirade.SIRADEAPI.repository.CitaMedicaRepository;
import com.sirade.SIRADEAPI.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitaMedicaService {

    @Autowired
    private CitaMedicaRepository citaRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    public CitaMedica agendarCita(Long pacienteId, CrearCitaDTO dto) {
        var paciente = usuarioRepo.findById(pacienteId).orElseThrow();
        var doctor = usuarioRepo.findById(dto.getDoctorId()).orElseThrow();

        CitaMedica cita = new CitaMedica();
        cita.setPaciente(paciente);
        cita.setDoctor(doctor);
        cita.setFecha(dto.getFecha());
        cita.setHora(dto.getHora());
        cita.setTipoConsulta(CitaMedica.TipoConsulta.valueOf(dto.getTipoConsulta()));
        cita.setNotas(dto.getNotas());

        return citaRepo.save(cita);
    }

    public List<CitaMedica> obtenerCitasPorPaciente(Long pacienteId) {
        return citaRepo.findByPacienteId(pacienteId);
    }

    public List<CitaMedica> obtenerCitasPorDoctor(Long doctorId) {
        return citaRepo.findByDoctorId(doctorId);
    }

}
