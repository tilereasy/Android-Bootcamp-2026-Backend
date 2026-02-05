package ru.sicampus.bootcamp2026.api;

import jakarta.validation.Valid;
import ru.sicampus.bootcamp2026.api.dto.MeetingRequest;
import ru.sicampus.bootcamp2026.api.dto.MeetingResponse;
import ru.sicampus.bootcamp2026.domain.Meeting;
import ru.sicampus.bootcamp2026.service.MeetingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/meetings")
public class MeetingController {
    private final MeetingService meetingService;

    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @GetMapping
    public Page<MeetingResponse> list(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return meetingService.list(pageable).map(MeetingController::toResponse);
    }

    @GetMapping("/{id}")
    public MeetingResponse get(@PathVariable Long id) {
        return toResponse(meetingService.get(id));
    }

    @PostMapping
    public ResponseEntity<MeetingResponse> create(@Valid @RequestBody MeetingRequest request) {
        Meeting created = meetingService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(created));
    }

    @PutMapping("/{id}")
    public MeetingResponse update(@PathVariable Long id, @Valid @RequestBody MeetingRequest request) {
        return toResponse(meetingService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        meetingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private static MeetingResponse toResponse(Meeting meeting) {
        return new MeetingResponse(
            meeting.getId(),
            meeting.getOrganizer().getId(),
            meeting.getTitle(),
            meeting.getDescription(),
            meeting.getStartAt(),
            meeting.getEndAt(),
            meeting.getCreatedAt()
        );
    }
}
