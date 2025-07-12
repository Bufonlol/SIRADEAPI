package com.sirade.SIRADEAPI.controller;

import com.sirade.SIRADEAPI.DTO.Medicamento;
import com.sirade.SIRADEAPI.DTO.MedicamentoDTO;
import com.sirade.SIRADEAPI.service.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentoController {

    @Autowired
    private MedicamentoService medicamentoService;

    @PostMapping("/agregar")
    public Medicamento agregarMedicamento(@RequestBody MedicamentoDTO dto) {
        return medicamentoService.agregarMedicamento(dto);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Medicamento> obtenerMedicamentos(@PathVariable Long usuarioId) {
        return medicamentoService.obtenerMedicamentosPorUsuario(usuarioId);
    }
}
