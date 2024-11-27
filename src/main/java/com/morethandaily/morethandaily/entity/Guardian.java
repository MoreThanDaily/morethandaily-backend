package com.morethandaily.morethandaily.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Guardian {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String inviteCode;

    @Column
    private Long userId; // 연결된 사용자 ID (단일 연결)

    // Constructor
    public Guardian(String name, String inviteCode) {
        this.name = name;
        this.inviteCode = inviteCode;
    }
}
