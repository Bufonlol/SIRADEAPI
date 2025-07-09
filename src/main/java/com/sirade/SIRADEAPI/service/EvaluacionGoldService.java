package com.sirade.SIRADEAPI.service;

import com.sirade.SIRADEAPI.DTO.EvaluacionGoldDTO;
import com.sirade.SIRADEAPI.DTO.EvaluacionGold;
import com.sirade.SIRADEAPI.DTO.UsuarioDTO;
import com.sirade.SIRADEAPI.repository.EvaluacionGoldRepository;
import com.sirade.SIRADEAPI.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EvaluacionGoldService {

    @Autowired
    private EvaluacionGoldRepository evaluacionGoldRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public EvaluacionGold guardarEvaluacion(Long usuarioId, EvaluacionGoldDTO dto) {
        UsuarioDTO usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String goldGrado;
        if (dto.getFev1Percent() >= 80) goldGrado = "GOLD 1";
        else if (dto.getFev1Percent() >= 50) goldGrado = "GOLD 2";
        else if (dto.getFev1Percent() >= 30) goldGrado = "GOLD 3";
        else goldGrado = "GOLD 4";

        boolean sintomas = dto.getCatScore() >= 10;
        boolean altoRiesgo = dto.getExacerbacionesGraves() >= 1 || dto.getExacerbacionesLeves() >= 2;

        String grupo = altoRiesgo ? "E" : (sintomas ? "B" : "A");

        EvaluacionGold evaluacion = new EvaluacionGold();
        evaluacion.setFev1Percent(dto.getFev1Percent());
        evaluacion.setCatScore(dto.getCatScore());
        evaluacion.setExacerbacionesLeves(dto.getExacerbacionesLeves());
        evaluacion.setExacerbacionesGraves(dto.getExacerbacionesGraves());
        evaluacion.setGoldGrado(goldGrado);
        evaluacion.setGrupo(grupo);
        evaluacion.setUsuario(usuario);

        return evaluacionGoldRepository.save(evaluacion);
    }

    public Optional<EvaluacionGold> obtenerEvaluacionPorUsuario(Long usuarioId) {
        return evaluacionGoldRepository.findByUsuarioId(usuarioId);
    }
}
