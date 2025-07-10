package com.sirade.SIRADEAPI.service;

import com.sirade.SIRADEAPI.DTO.EvaluacionGold;
import com.sirade.SIRADEAPI.DTO.EvaluacionGoldDTO;
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

    public EvaluacionGold guardarEvaluacion(Long userId, EvaluacionGoldDTO dto) {
        UsuarioDTO usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Calcular GOLD grado y grupo
        String goldGrado = calcularGradoGold(dto.getFev1Percent());
        String grupo = determinarGrupo(dto.getCatScore(), dto.getExacerbacionesLeves(), dto.getExacerbacionesGraves());

        EvaluacionGold evaluacion = new EvaluacionGold();
        evaluacion.setUsuario(usuario);
        evaluacion.setFev1Percent(dto.getFev1Percent());
        evaluacion.setCatScore(dto.getCatScore());
        evaluacion.setExacerbacionesLeves(dto.getExacerbacionesLeves());
        evaluacion.setExacerbacionesGraves(dto.getExacerbacionesGraves());
        evaluacion.setGoldGrado(goldGrado);
        evaluacion.setGrupo(grupo);

        // Asocia evaluación al usuario (bidireccional)
        usuario.setEvaluacionGold(evaluacion);
        usuario.setEvaluacionGoldCompleta(true);

        // Guarda ambos (cascade lo puede hacer, pero mejor explícito)
        usuarioRepository.save(usuario); // Esto guarda también la evaluación por cascada

        return evaluacion;
    }


    public Optional<EvaluacionGold> obtenerEvaluacionPorUsuario(Long userId) {
        return evaluacionGoldRepository.findByUsuarioId(userId);
    }

    private String calcularGradoGold(Integer fev1) {
        if (fev1 == null) return "Desconocido";
        if (fev1 >= 80) return "GOLD 1";
        if (fev1 >= 50) return "GOLD 2";
        if (fev1 >= 30) return "GOLD 3";
        return "GOLD 4";
    }

    private String determinarGrupo(int catScore, int leves, int graves) {
        int exacerbaciones = leves + graves;
        if (catScore < 10 && exacerbaciones <= 1) return "Grupo A";
        if (catScore >= 10 && exacerbaciones <= 1) return "Grupo B";
        if (catScore < 10 && exacerbaciones >= 2) return "Grupo C";
        return "Grupo D";
    }
}
