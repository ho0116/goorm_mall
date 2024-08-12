package com.example.goorm_mall.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.goorm_mall.model.Member;
import com.example.goorm_mall.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void join(String username, String email, String password, String rePassword) {
        if (memberRepository.existsByUsername(username)) {
            System.out.println("이미 존재하는 회원입니다.");
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
        else if (!password.equals(rePassword)) {
            System.out.println("비밀번호가 일치하지 않습니다.");
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
        Member member = new Member();
        member.setUsername(username);
        member.setEmail(email);
        member.setPassword(passwordEncoder.encode(password));
        member.setRole("ROLE_USER");

        memberRepository.save(member);
    }
    
    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }
}
