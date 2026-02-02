package ru.sicampus.bootcamp2026.repository;

import ru.sicampus.bootcamp2026.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
