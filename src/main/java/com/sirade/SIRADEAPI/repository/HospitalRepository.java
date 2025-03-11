package com.sirade.SIRADEAPI.repository;

import com.sirade.SIRADEAPI.DTO.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    Optional<Hospital> findByNombre(String nombre);

}