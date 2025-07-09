package com.sirade.SIRADEAPI.repository;

import com.sirade.SIRADEAPI.DTO.EvaluacionGold;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EvaluacionGoldRepository extends JpaRepository<EvaluacionGold, Long> {
    Optional<EvaluacionGold> findByUsuarioId(Long usuarioId);
}
