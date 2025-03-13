package com.sirade.SIRADEAPI.service;

import com.sirade.SIRADEAPI.DTO.ApiResponse;
import com.sirade.SIRADEAPI.DTO.AuthRequest;
import com.sirade.SIRADEAPI.DTO.AuthResponse;
import com.sirade.SIRADEAPI.DTO.UsuarioDTO;
import com.sirade.SIRADEAPI.jwt.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody AuthRequest authRequest) {
        try {
            // Realiza la autenticación con el AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),
                            authRequest.getPassword()
                    )
            );

            // Obtiene el email del usuario autenticado
            String email = authentication.getName();
            // Recupera el usuario completo de la base de datos
            UsuarioDTO usuario = usuarioService.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

            // Genera el token JWT con la información completa
            String jwt = jwtUtil.generateToken(usuario);

            return ResponseEntity.ok(
                    ApiResponse.success("Login exitoso", new AuthResponse(
                            jwt,
                            usuario.getId(),
                            usuario.getEmail(),
                            usuario.getRole().name(),
                            usuario.getNombreCompleto()
                    ))
            );

        } catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("Usuario inactivo"));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("Credenciales inválidas"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        try {
            if (usuarioService.existePorEmail(usuarioDTO.getEmail())) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("El email ya está registrado"));
            }
            // Forzar rol PACIENTE y estado ACTIVO para registros públicos
            usuarioDTO.setRole(UsuarioDTO.RolUsuario.PACIENTE);
            usuarioDTO.setStatus(UsuarioDTO.EstadoUsuario.ACTIVO);

            UsuarioDTO nuevoUsuario = usuarioService.guardar(usuarioDTO);
            String jwt = jwtUtil.generateToken(nuevoUsuario);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Registro exitoso", new AuthResponse(
                            jwt,
                            nuevoUsuario.getId(),
                            nuevoUsuario.getEmail(),
                            nuevoUsuario.getRole().name(),
                            nuevoUsuario.getNombreCompleto()
                    )));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Error en el registro: " + e.getMessage()));
        }
    }
}
