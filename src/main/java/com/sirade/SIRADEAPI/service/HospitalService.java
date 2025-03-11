package com.sirade.SIRADEAPI.service;

import com.sirade.SIRADEAPI.DTO.Hospital;
import com.sirade.SIRADEAPI.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    public List<Hospital> findAll() {
        return hospitalRepository.findAll();
    }

    public Optional<Hospital> findById(Long id) {
        return hospitalRepository.findById(id);
    }

    public Hospital save(Hospital hospital) {
        return hospitalRepository.save(hospital);
    }

    public Optional<Hospital> update(Long id, Hospital hospitalDetails) {
        return hospitalRepository.findById(id).map(hospital -> {
            hospital.setNombre(hospitalDetails.getNombre());
            hospital.setDireccion(hospitalDetails.getDireccion());
            hospital.setCiudad(hospitalDetails.getCiudad());
            hospital.setPais(hospitalDetails.getPais());
            hospital.setTelefono(hospitalDetails.getTelefono());
            hospital.setEmail(hospitalDetails.getEmail());
            return hospitalRepository.save(hospital);
        });
    }

    public boolean deleteById(Long id) {
        return hospitalRepository.findById(id).map(hospital -> {
            hospitalRepository.delete(hospital);
            return true;
        }).orElse(false);
    }
}