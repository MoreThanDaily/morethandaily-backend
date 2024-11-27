package com.morethandaily.morethandaily.repository;

import com.morethandaily.morethandaily.entity.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuardianRepository extends JpaRepository<Guardian, Long> {
    Optional<Guardian> findByInviteCode(String inviteCode);

}

