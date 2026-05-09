package com.turkcell.library_management.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.library_management.domain.Member;

public interface MemberRepository extends JpaRepository<Member, UUID> {
    Optional<Member> findByMemberNo(String memberNo);
    Optional<Member> findByEmail(String email);
}
