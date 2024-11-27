package com.morethandaily.morethandaily.service;

import com.morethandaily.morethandaily.entity.Guardian;
import com.morethandaily.morethandaily.entity.User;
import com.morethandaily.morethandaily.repository.UserRepository;
import com.morethandaily.morethandaily.repository.GuardianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final GuardianRepository guardianRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     */
    public User register(String name, String email, String password) {
        // 이메일 중복 확인
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);

        // 사용자 저장
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }

    /**
     * 로그인
     */
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        return user;
    }

    /**
     * ID로 사용자 검색
     */
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    /**
     * 보호자 연결
     */
    public void connectGuardian(Long userId, String inviteCode) {
        // 1. 사용자 검색
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 2. 초대 코드로 보호자 검색
        Guardian guardian = guardianRepository.findByInviteCode(inviteCode)
                .orElseThrow(() -> new IllegalArgumentException("Invalid invite code"));

        // 3. 사용자에 보호자 ID 설정
        user.setGuardianId(guardian.getId());

        // 4. 데이터베이스 저장
        userRepository.save(user);
    }
}
