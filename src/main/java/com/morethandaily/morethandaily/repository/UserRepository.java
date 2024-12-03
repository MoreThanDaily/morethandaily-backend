package com.morethandaily.morethandaily.repository;

import com.morethandaily.morethandaily.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);

    User findByGuardianId(Long guardianId);
}
