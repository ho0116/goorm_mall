package com.example.goorm_mall.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// 회원 로그인 id를 대신한다.
	@Column(nullable = false, length = 50)
    private String username;
	
	// 회원의 실제 이름
	@Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 100)
    private String email;
    
    @Column(nullable = false, length = 50)
    private String role;
    
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Payment> payments;    
}