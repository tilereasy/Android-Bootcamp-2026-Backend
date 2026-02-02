package ru.sicampus.bootcamp2026.service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import ru.sicampus.bootcamp2026.api.error.NotFoundException;
import ru.sicampus.bootcamp2026.api.dto.UserRequest;
import ru.sicampus.bootcamp2026.domain.User;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> list() {
        return userRepository.findAll();
    }

    public User get(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("User not found: " + id));
    }

    public User create(UserRequest request) {
        User user = new User();
        apply(user, request);
        user.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        return userRepository.save(user);
    }

    public User update(Long id, UserRequest request) {
        User user = get(id);
        apply(user, request);
        return userRepository.save(user);
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User not found: " + id);
        }
        userRepository.deleteById(id);
    }

    private void apply(User user, UserRequest request) {
        user.setEmail(request.email());
        user.setPasswordHash(request.passwordHash());
        user.setFullName(request.fullName());
        user.setDepartment(request.department());
        user.setPosition(request.position());
    }
}
