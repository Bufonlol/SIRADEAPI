package com.sirade.SIRADEAPI.repository;

import com.sirade.SIRADEAPI.DTO.CitaMedica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CitaMedicaRepository extends JpaRepository<CitaMedica, Long> {

    List<CitaMedica> findByPacienteId(Long pacienteId);
    List<CitaMedica> findByDoctorId(Long doctorId);

}
