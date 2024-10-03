package com.lezhin.assignment.domain.memeber.repository;

import com.lezhin.assignment.domain.memeber.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email); // 중복 가입 방지와 존재여부를 파악
}
