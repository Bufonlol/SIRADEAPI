package com.sirade.SIRADEAPI.controller;

import com.sirade.SIRADEAPI.DTO.UsuarioDTO;
import com.sirade.SIRADEAPI.repository.UsuarioRepository;
import com.sirade.SIRADEAPI.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Obtener todos los usuarios (solo ADMIN)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/doctores/hospital/{hospitalId}")
    public List<UsuarioDTO> listarDoctoresPorHospital(@PathVariable Long hospitalId) {
        return usuarioRepository.findByHospital_IdAndRole(hospitalId, UsuarioDTO.RolUsuario.DOCTOR);
    }


    // Obtener usuarios paginados (solo ADMIN)
    @GetMapping("/paged")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UsuarioDTO>> getAllUsuariosPaged(Pageable pageable) {
        return ResponseEntity.ok(usuarioService.listarTodosPaginados(pageable));
    }

    // Obtener usuario por ID (Admin o propio usuario)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable Long id) {
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/doctores")
    public List<UsuarioDTO> obtenerDoctores() {
        return usuarioService.obtenerDoctores();
    }


    // Crear usuario (abierto o según política de seguridad)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UsuarioDTO> createUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.guardar(usuarioDTO));
    }

    // Actualizar usuario (Admin o propio usuario)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    public ResponseEntity<UsuarioDTO> updateUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioDTO usuarioDTO
    ) {
        return usuarioService.actualizar(id, usuarioDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        if (usuarioService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Nuevo endpoint: Filtrar por rol
    @GetMapping("/rol/{rol}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioDTO>> getUsuariosPorRol(@PathVariable UsuarioDTO.RolUsuario rol) {
        return ResponseEntity.ok(usuarioService.buscarPorRol(rol));
    }


}