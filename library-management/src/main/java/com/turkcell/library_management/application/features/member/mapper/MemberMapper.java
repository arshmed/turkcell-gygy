package com.turkcell.library_management.application.features.member.mapper;

import org.springframework.stereotype.Component;

import com.turkcell.library_management.application.features.member.command.create.CreateMemberCommand;
import com.turkcell.library_management.application.features.member.command.create.CreatedMemberResponse;
import com.turkcell.library_management.application.features.member.query.getall.GetAllMembersResponse;
import com.turkcell.library_management.domain.Member;

@Component
public class MemberMapper {
    public Member memberFromCreateCommand(CreateMemberCommand command) {
        Member member = new Member();
        member.setMemberNo(command.memberNo());
        member.setFirstName(command.firstName());
        member.setLastName(command.lastName());
        member.setEmail(command.email());
        member.setPhone(command.phone());
        member.setDepartment(command.department());
        return member;
    }

    public CreatedMemberResponse createdMemberResponseFromMember(Member member) {
        return new CreatedMemberResponse(member.getId(), member.getMemberNo(), member.getFirstName(), member.getLastName());
    }

    public GetAllMembersResponse getAllMembersResponseFromMember(Member member) {
        return new GetAllMembersResponse(
            member.getId(), member.getMemberNo(), member.getFirstName(), member.getLastName(),
            member.getEmail(), member.getPhone(), member.getDepartment(),
            member.getRegistrationDate(), member.getStatus().name()
        );
    }
}
