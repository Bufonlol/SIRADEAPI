package com.sirade.SIRADEAPI.controller;

import com.sirade.SIRADEAPI.DTO.Hospital;
import com.sirade.SIRADEAPI.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/hospitales")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @GetMapping
    public ResponseEntity<List<Hospital>> getAllHospitales() {
        List<Hospital> hospitales = hospitalService.findAll();
        return new ResponseEntity<>(hospitales, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hospital> getHospitalById(@PathVariable Long id) {
        return hospitalService.findById(id)
                .map(hospital -> new ResponseEntity<>(hospital, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Hospital> createHospital(@RequestBody Hospital hospital) {
        Hospital nuevoHospital = hospitalService.save(hospital);
        return new ResponseEntity<>(nuevoHospital, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Hospital> updateHospital(@PathVariable Long id, @RequestBody Hospital hospitalDetails) {
        return hospitalService.update(id, hospitalDetails)
                .map(hospital -> new ResponseEntity<>(hospital, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteHospital(@PathVariable Long id) {
        if (hospitalService.deleteById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}