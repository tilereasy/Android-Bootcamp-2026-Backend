package ru.sicampus.bootcamp2026.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequest(
    @NotBlank @Email String email,
    @NotBlank String passwordHash,
    String fullName,
    String department,
    String position
) {
}
