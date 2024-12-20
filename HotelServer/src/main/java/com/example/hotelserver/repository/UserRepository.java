package com.example.hotelserver.repository;

import com.example.hotelserver.entity.User;
import com.example.hotelserver.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByEmail(String email);
    Optional<User> findUserByUserRole(UserRole userRole);
}
