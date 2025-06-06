package com.sirade.SIRADEAPI.config;

import com.sirade.SIRADEAPI.DTO.UsuarioDTO;
import com.sirade.SIRADEAPI.DTO.UsuarioDTO.EstadoUsuario;
import com.sirade.SIRADEAPI.DTO.UsuarioDTO.RolUsuario;
import com.sirade.SIRADEAPI.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DefaultAdminInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Verifica si ya existe un admin
        if (usuarioRepository.findByEmail("admin@sirade.com").isEmpty()) {
            UsuarioDTO admin = new UsuarioDTO();
            admin.setFirstName("Admin");
            admin.setLastName("SIRADE");
            admin.setEmail("admin@sirade.com");
            admin.setPassword(passwordEncoder.encode("admin123")); // Asegúrate de usar una contraseña segura
            admin.setRole(RolUsuario.ADMIN);
            admin.setStatus(EstadoUsuario.ACTIVO);
            admin.setSpecialty("Administración");

            usuarioRepository.save(admin);
            System.out.println("✅ Usuario administrador creado por defecto.");
        }
    }
}
