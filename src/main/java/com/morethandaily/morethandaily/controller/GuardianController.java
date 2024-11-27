package com.morethandaily.morethandaily.controller;

import com.morethandaily.morethandaily.dto.RegisterRequest;
import com.morethandaily.morethandaily.dto.LoginRequest;
import com.morethandaily.morethandaily.entity.Guardian;
import com.morethandaily.morethandaily.service.GuardianService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/guardian")
public class GuardianController {

    private final GuardianService guardianService;

    public GuardianController(GuardianService guardianService) {
        this.guardianService = guardianService;
    }

    // 보호자 회원가입 API
    @PostMapping("/register")
    public ResponseEntity<?> registerGuardian(@RequestBody RegisterRequest request) {
        try {
            Guardian guardian = guardianService.register(request.getName(), request.getEmail(), request.getPassword());
            return ResponseEntity.ok(guardian); // inviteCode 포함하여 반환
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // 보호자 로그인 API
    @PostMapping("/login")
    public ResponseEntity<?> loginGuardian(@RequestBody LoginRequest request) {
        try {
            Guardian guardian = guardianService.login(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(guardian);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/connect")
    public ResponseEntity<String> connectGuardian(@RequestParam String inviteCode) {
        guardianService.connectGuardian(inviteCode);
        return ResponseEntity.ok("Guardian connected successfully");
    }
}
