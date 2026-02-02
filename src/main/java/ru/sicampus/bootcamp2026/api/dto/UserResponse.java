package ru.sicampus.bootcamp2026.api.dto;

import java.time.OffsetDateTime;

public record UserResponse(
    Long id,
    String email,
    String passwordHash,
    String fullName,
    String department,
    String position,
    OffsetDateTime createdAt
) {
}
