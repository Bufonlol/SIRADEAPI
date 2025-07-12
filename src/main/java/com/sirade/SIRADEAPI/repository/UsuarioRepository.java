package com.sirade.SIRADEAPI.repository;

import com.sirade.SIRADEAPI.DTO.UsuarioDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioDTO, Long> {
    Optional<UsuarioDTO> findByEmail(String email);
    boolean existsByEmail(String email);
    List<UsuarioDTO> findByRole(UsuarioDTO.RolUsuario rol);
    @Query("SELECT u FROM UsuarioDTO u WHERE u.email = ?1 AND u.status = 'ACTIVO'")
    Optional<UsuarioDTO> findByEmailAndStatus(String email, UsuarioDTO.EstadoUsuario status);

    List<UsuarioDTO> findByHospital_IdAndRole(Long hospitalId, UsuarioDTO.RolUsuario role);


}