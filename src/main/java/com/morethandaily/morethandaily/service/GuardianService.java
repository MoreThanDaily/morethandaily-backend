package com.morethandaily.morethandaily.service;
import com.morethandaily.morethandaily.entity.Guardian;
import com.morethandaily.morethandaily.entity.User;
import com.morethandaily.morethandaily.repository.GuardianRepository;
import com.morethandaily.morethandaily.repository.UserRepository;
import com.morethandaily.morethandaily.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class GuardianService {

    private final GuardianRepository guardianRepository;
    private final UserRepository userRepository;

    public GuardianService(GuardianRepository guardianRepository, UserRepository userRepository) {
        this.guardianRepository = guardianRepository;
        this.userRepository = userRepository;
    }

    public void connectGuardian(String inviteCode) {
        // 1. 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Long userId = userDetails.getUserId(); // 사용자 ID 가져오기

        // 2. User 검증
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 3. Invite Code 검증
        Guardian guardian = guardianRepository.findByInviteCode(inviteCode)
                .orElseThrow(() -> new IllegalArgumentException("Invalid invite code"));

        // 4. 관계 설정
        user.setGuardianId(guardian.getId());
        guardian.setUserId(user.getId());

        // 5. 저장
        userRepository.save(user);
        guardianRepository.save(guardian);
    }
}
