package com.morethandaily.morethandaily.service;

import com.morethandaily.morethandaily.entity.Guardian;
import com.morethandaily.morethandaily.repository.GuardianRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GuardianService {

    private final GuardianRepository guardianRepository;
    private final PasswordEncoder passwordEncoder;

    public GuardianService(GuardianRepository guardianRepository, PasswordEncoder passwordEncoder) {
        this.guardianRepository = guardianRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입 로직
    public Guardian register(String name, String email, String password) {
        if (guardianRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already in use");
        }

        // 고유 inviteCode 생성
        String inviteCode = UUID.randomUUID().toString();

        Guardian guardian = new Guardian();
        guardian.setName(name);
        guardian.setEmail(email);
        guardian.setPassword(passwordEncoder.encode(password));
        guardian.setInviteCode(generateInviteCode()); // 생성된 inviteCode 설정

        return guardianRepository.save(guardian);
    }

    // 로그인 로직
    public Guardian login(String email, String password) {
        Guardian guardian = guardianRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(password, guardian.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return guardian;
    }

    public Guardian connectGuardian(String inviteCode) {
        // 초대 코드로 Guardian 검색
        Optional<Guardian> guardianOptional = guardianRepository.findByInviteCode(inviteCode);

        if (guardianOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid invite code");
        }

        Guardian guardian = guardianOptional.get();

        // 추가적인 연결 작업이 있다면 여기에 작성
        // 예: 사용자와 Guardian을 관계 설정

        return guardian;
    }
    private String generateInviteCode() {
        // UUID를 사용하여 고유 초대 코드 생성
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
    }
}
