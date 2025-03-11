package com.sirade.SIRADEAPI.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Email
    private String email;

    public @NotBlank String getFirstName() {        return firstName;
    }

    public void setFirstName(@NotBlank String firstName) {
        this.firstName = firstName;
    }

    public @NotBlank String getLastName() {
        return lastName;
    }

    public void setLastName(@NotBlank String lastName) {
        this.lastName = lastName;
    }

    public @NotBlank @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank @Email String email) {
        this.email = email;
    }

    public @NotBlank @Size(min = 6) String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank @Size(min = 6) String password) {
        this.password = password;
    }

    public @NotBlank String getHospital() {
        return hospital;
    }

    public void setHospital(@NotBlank String hospital) {
        this.hospital = hospital;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    @NotBlank
    @Size(min = 6)
    private String password;

    @NotBlank
    private String hospital;

    private String specialty;

    // Getters y Setters
}