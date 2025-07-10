package com.sirade.SIRADEAPI.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "usuarios")
public class UsuarioDTO {

    public enum RolUsuario { ADMIN, COORDINADOR, DOCTOR, PACIENTE }

    public enum EstadoUsuario { ACTIVO, INACTIVO }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Boolean getEvaluacionGoldCompleta() {
        return evaluacionGoldCompleta;
    }

    public void setEvaluacionGoldCompleta(Boolean evaluacionGoldCompleta) {
        this.evaluacionGoldCompleta = evaluacionGoldCompleta;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EvaluacionGold getEvaluacionGold() {
        return evaluacionGold;
    }

    public void setEvaluacionGold(EvaluacionGold evaluacionGold) {
        this.evaluacionGold = evaluacionGold;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RolUsuario getRole() {
        return role;
    }

    public void setRole(RolUsuario role) {
        this.role = role;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public EstadoUsuario getStatus() {
        return status;
    }

    public void setStatus(EstadoUsuario status) {
        this.status = status;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    @NotBlank
    private String firstName;

    @Column(name = "evaluacion_gold_completa")
    private Boolean evaluacionGoldCompleta = false;  // Cambiado a Boolean con valor por defecto

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private EvaluacionGold evaluacionGold;

    @NotBlank
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RolUsuario role;

    private String specialty;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EstadoUsuario status = EstadoUsuario.ACTIVO;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ManyToOne
    @JoinColumn(name = "hospital_id")
    @JsonBackReference // Evita la serialización infinita
    private Hospital hospital;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    private Long hospitalId;

    // Getters y setters omitidos para brevedad (puedes mantenerlos como están)

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    public String getNombreCompleto() {
        return firstName + " " + lastName;
    }

    @PrePersist
    @PreUpdate
    private void prePersist() {
        if (role == null) role = RolUsuario.PACIENTE;
        if (status == null) status = EstadoUsuario.ACTIVO;
        if (evaluacionGoldCompleta == null) evaluacionGoldCompleta = false;  // Por seguridad
    }
}
