package com.turkcell.library_management.application.features.member.rule;

import org.springframework.stereotype.Component;

import com.turkcell.library_management.domain.Member;
import com.turkcell.library_management.persistence.repository.MemberRepository;

@Component
public class MemberBusinessRules {
    private final MemberRepository memberRepository;

    public MemberBusinessRules(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void memberWithSameMemberNoMustNotExist(String memberNo) {
        Member member = memberRepository.findByMemberNo(memberNo).orElse(null);
        if (member != null)
            throw new RuntimeException("Aynı üye numarası ile 2 üye eklenemez");
    }
}
