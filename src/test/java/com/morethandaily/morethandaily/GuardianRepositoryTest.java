package com.morethandaily.morethandaily;

import com.morethandaily.morethandaily.entity.Guardian;
import com.morethandaily.morethandaily.repository.GuardianRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional // 테스트 데이터 변경 후 롤백을 보장
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GuardianRepositoryTest {

    @Autowired
    private GuardianRepository guardianRepository;

    @Test
    public void testSaveGuardian() {
        // Given: Guardian 생성
        Guardian guardian = new Guardian("Alice", "INVITE001");

        // When: 저장
        Guardian savedGuardian = guardianRepository.save(guardian);

        // Then: 검증
        assertNotNull(savedGuardian.getId(), "Guardian ID should not be null after saving");
        assertEquals("Alice", savedGuardian.getName(), "Guardian name should match");
        assertEquals("INVITE001", savedGuardian.getInviteCode(), "Guardian invite code should match");
    }

    @Test
    public void testFindByInviteCode() {
        // Given: Guardian 저장
        Guardian guardian = new Guardian("Bob", "INVITE002");
        guardianRepository.save(guardian);

        // When: Invite Code로 검색
        Optional<Guardian> foundGuardian = guardianRepository.findByInviteCode("INVITE002");

        // Then: 검증
        assertTrue(foundGuardian.isPresent(), "Guardian with the invite code should exist");
        assertEquals("Bob", foundGuardian.get().getName(), "Guardian name should match");
        assertEquals("INVITE002", foundGuardian.get().getInviteCode(), "Invite code should match");
    }

    @Test
    public void testDeleteGuardian() {
        // Given: Guardian 저장
        Guardian guardian = new Guardian("Charlie", "INVITE003");
        guardianRepository.save(guardian);

        // When: 삭제
        guardianRepository.delete(guardian);

        // Then: 검증
        Optional<Guardian> deletedGuardian = guardianRepository.findByInviteCode("INVITE003");
        assertFalse(deletedGuardian.isPresent(), "Deleted guardian should not exist");
    }
}
