package org.example.api.dto;

import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import org.example.domain.InvitationStatus;

public record InvitationRequest(
    @NotNull Long meetingId,
    @NotNull Long inviteeId,
    InvitationStatus status,
    OffsetDateTime respondedAt
) {
}
