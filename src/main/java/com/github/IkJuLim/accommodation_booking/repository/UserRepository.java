package com.github.IkJuLim.accommodation_booking.repository;

import com.github.IkJuLim.accommodation_booking.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
