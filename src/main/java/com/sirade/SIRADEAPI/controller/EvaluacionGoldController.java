package com.sirade.SIRADEAPI.controller;

import com.sirade.SIRADEAPI.DTO.EvaluacionGoldDTO;
import com.sirade.SIRADEAPI.DTO.EvaluacionGold;
import com.sirade.SIRADEAPI.repository.UsuarioRepository;
import com.sirade.SIRADEAPI.service.EvaluacionGoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/evaluacion-gold")
public class EvaluacionGoldController {

    @Autowired
    private UsuarioRepository usuarioRepository;  // <-- falta esta inyección

    @Autowired
    private EvaluacionGoldService evaluacionGoldService;

    @PostMapping
    @PreAuthorize("hasRole('PACIENTE')")
    public EvaluacionGold evaluarYGuardar(@RequestBody EvaluacionGoldDTO dto, Principal principal) {
        Long userId = getUserIdFromPrincipal(principal);
        return evaluacionGoldService.guardarEvaluacion(userId, dto);
    }

    @GetMapping
    @PreAuthorize("hasRole('PACIENTE')")
    public Optional<EvaluacionGold> getEvaluacion(Principal principal) {
        Long userId = getUserIdFromPrincipal(principal);
        return evaluacionGoldService.obtenerEvaluacionPorUsuario(userId);
    }

    private Long getUserIdFromPrincipal(Principal principal) {
        if (principal == null) {
            throw new RuntimeException("Usuario no autenticado");
        }
        String email = principal.getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email))
                .getId();
    }
}
