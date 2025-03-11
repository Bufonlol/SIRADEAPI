package com.sirade.SIRADEAPI.DTO;

public class AuthResponse {
    private String token;
    private Long userId;
    private String email;
    private String rol;
    private String nombreCompleto;

    // Constructor con todos los campos
    public AuthResponse(String token, Long userId, String email, String rol, String nombreCompleto) {
        this.token = token;
        this.userId = userId;
        this.email = email;
        this.rol = rol;
        this.nombreCompleto = nombreCompleto;
    }

    // Getters
    public String getToken() { return token; }
    public Long getUserId() { return userId; }
    public String getEmail() { return email; }
    public String getRol() { return rol; }
    public String getNombreCompleto() { return nombreCompleto; }
}