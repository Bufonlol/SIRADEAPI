package com.sirade.SIRADEAPI.service;

import com.sirade.SIRADEAPI.DTO.Medicamento;
import com.sirade.SIRADEAPI.DTO.MedicamentoDTO;
import com.sirade.SIRADEAPI.DTO.UsuarioDTO;
import com.sirade.SIRADEAPI.repository.MedicamentoRepository;
import com.sirade.SIRADEAPI.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class MedicamentoService {

    @Autowired
    private MedicamentoRepository medicamentoRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    public Medicamento agregarMedicamento(MedicamentoDTO dto) {
        UsuarioDTO usuario = usuarioRepo.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Medicamento med = new Medicamento();
        med.setNombre(dto.getNombre());
        med.setDosis(dto.getDosis());
        med.setFrecuencia(dto.getFrecuencia());
        med.setHora(LocalTime.parse(dto.getHora())); // formato HH:mm
        med.setNotas(dto.getNotas());
        med.setRecordatorio(dto.getRecordatorio());
        med.setUsuario(usuario);

        return medicamentoRepo.save(med);
    }

    public List<Medicamento> obtenerMedicamentosPorUsuario(Long usuarioId) {
        return medicamentoRepo.findByUsuarioId(usuarioId);
    }
}
