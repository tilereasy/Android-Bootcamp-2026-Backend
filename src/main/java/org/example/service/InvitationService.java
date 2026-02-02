package org.example.service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.example.api.dto.InvitationRequest;
import org.example.api.error.NotFoundException;
import org.example.domain.Invitation;
import org.example.domain.InvitationStatus;
import org.example.domain.Meeting;
import org.example.domain.User;
import org.example.repository.InvitationRepository;
import org.example.repository.MeetingRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class InvitationService {
    private final InvitationRepository invitationRepository;
    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;

    public InvitationService(
        InvitationRepository invitationRepository,
        MeetingRepository meetingRepository,
        UserRepository userRepository
    ) {
        this.invitationRepository = invitationRepository;
        this.meetingRepository = meetingRepository;
        this.userRepository = userRepository;
    }

    public List<Invitation> list() {
        return invitationRepository.findAll();
    }

    public Invitation get(Long id) {
        return invitationRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Invitation not found: " + id));
    }

    public Invitation create(InvitationRequest request) {
        Invitation invitation = new Invitation();
        apply(invitation, request);
        invitation.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        return invitationRepository.save(invitation);
    }

    public Invitation update(Long id, InvitationRequest request) {
        Invitation invitation = get(id);
        apply(invitation, request);
        return invitationRepository.save(invitation);
    }

    public void delete(Long id) {
        if (!invitationRepository.existsById(id)) {
            throw new NotFoundException("Invitation not found: " + id);
        }
        invitationRepository.deleteById(id);
    }

    private void apply(Invitation invitation, InvitationRequest request) {
        Meeting meeting = meetingRepository.findById(request.meetingId())
            .orElseThrow(() -> new NotFoundException("Meeting not found: " + request.meetingId()));
        User invitee = userRepository.findById(request.inviteeId())
            .orElseThrow(() -> new NotFoundException("User not found: " + request.inviteeId()));
        invitation.setMeeting(meeting);
        invitation.setInvitee(invitee);
        invitation.setStatus(request.status() == null ? InvitationStatus.PENDING : request.status());
        invitation.setRespondedAt(request.respondedAt());
    }
}
