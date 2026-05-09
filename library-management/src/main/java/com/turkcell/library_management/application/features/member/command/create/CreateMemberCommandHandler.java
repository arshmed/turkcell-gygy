package com.turkcell.library_management.application.features.member.command.create;

import org.springframework.stereotype.Component;

import com.turkcell.library_management.application.features.member.mapper.MemberMapper;
import com.turkcell.library_management.application.features.member.rule.MemberBusinessRules;
import com.turkcell.library_management.core.mediator.cqrs.CommandHandler;
import com.turkcell.library_management.domain.Member;
import com.turkcell.library_management.persistence.repository.MemberRepository;

@Component
public class CreateMemberCommandHandler implements CommandHandler<CreateMemberCommand, CreatedMemberResponse> {
    private final MemberRepository memberRepository;
    private final MemberBusinessRules memberBusinessRules;
    private final MemberMapper memberMapper;

    public CreateMemberCommandHandler(MemberRepository memberRepository,
            MemberBusinessRules memberBusinessRules, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberBusinessRules = memberBusinessRules;
        this.memberMapper = memberMapper;
    }

    @Override
    public CreatedMemberResponse handle(CreateMemberCommand command) {
        memberBusinessRules.memberWithSameMemberNoMustNotExist(command.memberNo());

        Member member = memberMapper.memberFromCreateCommand(command);

        memberRepository.save(member);

        return memberMapper.createdMemberResponseFromMember(member);
    }
}
