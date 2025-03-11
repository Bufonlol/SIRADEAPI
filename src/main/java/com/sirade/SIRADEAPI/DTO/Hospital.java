package com.sirade.SIRADEAPI.DTO;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@Data
@Entity
@Table(name = "hospitales")
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "El nombre es obligatorio") String getNombre() {
        return nombre;
    }

    public void setNombre(@NotBlank(message = "El nombre es obligatorio") String nombre) {
        this.nombre = nombre;
    }

    public @NotBlank(message = "La dirección es obligatoria") String getDireccion() {
        return direccion;
    }

    public void setDireccion(@NotBlank(message = "La dirección es obligatoria") String direccion) {
        this.direccion = direccion;
    }

    public @NotBlank(message = "La ciudad es obligatoria") String getCiudad() {
        return ciudad;
    }

    public void setCiudad(@NotBlank(message = "La ciudad es obligatoria") String ciudad) {
        this.ciudad = ciudad;
    }

    public @NotBlank(message = "El país es obligatorio") String getPais() {
        return pais;
    }

    public void setPais(@NotBlank(message = "El país es obligatorio") String pais) {
        this.pais = pais;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<UsuarioDTO> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioDTO> usuarios) {
        this.usuarios = usuarios;
    }

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @NotBlank(message = "La ciudad es obligatoria")
    private String ciudad;

    @NotBlank(message = "El país es obligatorio")
    private String pais;

    private String telefono;
    private String email;

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL)
    private List<UsuarioDTO> usuarios;

    // Constructor vacío requerido por Jackson
    public Hospital() {}

    // Constructor con parámetros para facilitar pruebas o creación de objetos
    @JsonCreator
    public Hospital(
            @JsonProperty("nombre") String nombre,
            @JsonProperty("direccion") String direccion,
            @JsonProperty("ciudad") String ciudad,
            @JsonProperty("pais") String pais,
            @JsonProperty("telefono") String telefono,
            @JsonProperty("email") String email
    ) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.pais = pais;
        this.telefono = telefono;
        this.email = email;
    }
}
