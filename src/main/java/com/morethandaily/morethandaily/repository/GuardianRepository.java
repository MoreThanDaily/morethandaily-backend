package com.morethandaily.morethandaily.repository;

import com.morethandaily.morethandaily.entity.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuardianRepository extends JpaRepository<Guardian, Long> {

    // 이메일로 Guardian 검색
    Optional<Guardian> findByEmail(String email);

    // inviteCode로 Guardian 검색
    Optional<Guardian> findByInviteCode(String inviteCode);

    // 이메일 중복 여부 확인
    boolean existsByEmail(String email);

    // inviteCode 중복 여부 확인
    boolean existsByInviteCode(String inviteCode);
}
