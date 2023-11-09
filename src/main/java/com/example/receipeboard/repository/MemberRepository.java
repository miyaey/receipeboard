package com.example.receipeboard.repository;

import com.example.receipeboard.data.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findBynickname(String nickname);
}
