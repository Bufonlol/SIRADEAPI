package com.sirade.SIRADEAPI.service;

import com.sirade.SIRADEAPI.DTO.Hospital;
import com.sirade.SIRADEAPI.DTO.UsuarioDTO;
import com.sirade.SIRADEAPI.repository.HospitalRepository;
import com.sirade.SIRADEAPI.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    public List<UsuarioDTO> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Page<UsuarioDTO> listarTodosPaginados(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    public Optional<UsuarioDTO> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public boolean existePorEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public UsuarioDTO guardar(UsuarioDTO usuarioDTO) {
        if (usuarioDTO.getPassword() == null || usuarioDTO.getPassword().isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }
        usuarioDTO.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));

        // Validación del hospital si se proporciona
        if (usuarioDTO.getHospitalId() != null) {
            Hospital hospital = hospitalRepository.findById(usuarioDTO.getHospitalId())
                    .orElseThrow(() -> new RuntimeException("Hospital no encontrado"));
            usuarioDTO.setHospital(hospital);
        }

        return usuarioRepository.save(usuarioDTO);
    }

    public Optional<UsuarioDTO> actualizar(Long id, UsuarioDTO usuarioDTO) {
        return usuarioRepository.findById(id).map(existente -> {
            existente.setFirstName(usuarioDTO.getFirstName());
            existente.setLastName(usuarioDTO.getLastName());

            if (!existente.getEmail().equals(usuarioDTO.getEmail()) &&
                    usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
                throw new IllegalArgumentException("El email ya está en uso");
            }
            existente.setEmail(usuarioDTO.getEmail());

            if (usuarioDTO.getPassword() != null && !usuarioDTO.getPassword().isBlank()) {
                existente.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
            }

            if (usuarioDTO.getHospitalId() != null) {
                Hospital hospital = hospitalRepository.findById(usuarioDTO.getHospitalId())
                        .orElseThrow(() -> new RuntimeException("Hospital no encontrado"));
                existente.setHospital(hospital);
            } else {
                existente.setHospital(null);
            }

            // Solo un usuario con rol ADMIN puede actualizar rol y estado
            Authentication auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                existente.setRole(usuarioDTO.getRole());
                existente.setStatus(usuarioDTO.getStatus());
            }

            return usuarioRepository.save(existente);
        });
    }

    public List<UsuarioDTO> buscarPorRol(UsuarioDTO.RolUsuario rol) {
        return usuarioRepository.findByRole(rol);
    }

    public boolean eliminar(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<UsuarioDTO> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsuarioDTO usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        if (usuario.getStatus() != UsuarioDTO.EstadoUsuario.ACTIVO) {
            throw new DisabledException("Usuario inactivo");
        }

        return new org.springframework.security.core.userdetails.User(
                usuario.getEmail(),
                usuario.getPassword(),
                usuario.getAuthorities());
    }
}
