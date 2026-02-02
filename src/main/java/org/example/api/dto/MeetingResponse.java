package org.example.api.dto;

import java.time.OffsetDateTime;

public record MeetingResponse(
    Long id,
    Long organizerId,
    String title,
    String description,
    OffsetDateTime startAt,
    OffsetDateTime endAt,
    OffsetDateTime createdAt
) {
}
