package com.sirade.SIRADEAPI.repository;

import com.sirade.SIRADEAPI.DTO.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {
    List<Medicamento> findByUsuarioId(Long usuarioId);
}

