package com.playground.th.repository;

import com.playground.th.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Member findByEmailAndPassword(String email, String pw);

    Optional<Member> findByEmail(String email);
}
