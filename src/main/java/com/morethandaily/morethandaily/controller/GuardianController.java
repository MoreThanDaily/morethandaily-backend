package com.morethandaily.morethandaily.controller;

import com.morethandaily.morethandaily.service.GuardianService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/guardian")
public class GuardianController {

    private final GuardianService guardianService;

    public GuardianController(GuardianService guardianService) {
        this.guardianService = guardianService;
    }

    @PostMapping("/connect")
    public ResponseEntity<String> connectGuardian(@RequestParam String inviteCode) {
        guardianService.connectGuardian(inviteCode);
        return ResponseEntity.ok("Guardian connected successfully");
    }
}
