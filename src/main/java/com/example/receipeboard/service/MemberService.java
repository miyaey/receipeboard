package com.example.receipeboard.service;

import com.example.receipeboard.config.DataNotFoundException;
import com.example.receipeboard.data.dto.MemberResponse;
import com.example.receipeboard.data.entity.Member;
import com.example.receipeboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;  // PasswordEncoder 주입
    private final ModelMapper modelMapper;

    private MemberResponse of(Member member) {
        return this.modelMapper.map(member, MemberResponse.class);
    }

    public MemberResponse create(String nickname, String password, String name) {
        Member member = new Member();
        member.setNickname(nickname);
        member.setPassword(passwordEncoder.encode(password));
        member.setName(name);
        this.memberRepository.save(member);
        return of(member);
    }

    public MemberResponse getMember(String nickname) {
        Optional<Member> member = this.memberRepository.findBynickname(nickname);
        if (member.isPresent()) {
            return of(member.get());
        } else {
            throw new DataNotFoundException("Member not found");
        }
    }
}