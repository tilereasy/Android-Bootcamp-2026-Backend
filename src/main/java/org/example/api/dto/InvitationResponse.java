package org.example.api.dto;

import java.time.OffsetDateTime;
import org.example.domain.InvitationStatus;

public record InvitationResponse(
    Long id,
    Long meetingId,
    Long inviteeId,
    InvitationStatus status,
    OffsetDateTime respondedAt,
    OffsetDateTime createdAt
) {
}
