package com.github.IkJuLim.accommodation_booking.repository;

import com.github.IkJuLim.accommodation_booking.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
