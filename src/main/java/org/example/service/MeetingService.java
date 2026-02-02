package org.example.service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.example.api.dto.MeetingRequest;
import org.example.api.error.BadRequestException;
import org.example.api.error.NotFoundException;
import org.example.domain.Meeting;
import org.example.domain.User;
import org.example.repository.MeetingRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;

    public MeetingService(MeetingRepository meetingRepository, UserRepository userRepository) {
        this.meetingRepository = meetingRepository;
        this.userRepository = userRepository;
    }

    public List<Meeting> list() {
        return meetingRepository.findAll();
    }

    public Meeting get(Long id) {
        return meetingRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Meeting not found: " + id));
    }

    public Meeting create(MeetingRequest request) {
        Meeting meeting = new Meeting();
        apply(meeting, request);
        meeting.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        return meetingRepository.save(meeting);
    }

    public Meeting update(Long id, MeetingRequest request) {
        Meeting meeting = get(id);
        apply(meeting, request);
        return meetingRepository.save(meeting);
    }

    public void delete(Long id) {
        if (!meetingRepository.existsById(id)) {
            throw new NotFoundException("Meeting not found: " + id);
        }
        meetingRepository.deleteById(id);
    }

    private void apply(Meeting meeting, MeetingRequest request) {
        if (request.endAt().isBefore(request.startAt())) {
            throw new BadRequestException("endAt must be after startAt");
        }
        User organizer = userRepository.findById(request.organizerId())
            .orElseThrow(() -> new NotFoundException("Organizer not found: " + request.organizerId()));
        meeting.setOrganizer(organizer);
        meeting.setTitle(request.title());
        meeting.setDescription(request.description());
        meeting.setStartAt(request.startAt());
        meeting.setEndAt(request.endAt());
    }
}
