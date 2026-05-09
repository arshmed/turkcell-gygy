package com.turkcell.library_management.application.features.member.query.getall;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.turkcell.library_management.application.features.member.mapper.MemberMapper;
import com.turkcell.library_management.core.mediator.cqrs.QueryHandler;
import com.turkcell.library_management.domain.Member;
import com.turkcell.library_management.persistence.repository.MemberRepository;

@Component
public class GetAllMembersQueryHandler implements QueryHandler<GetAllMembersQuery, Page<GetAllMembersResponse>> {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    public GetAllMembersQueryHandler(MemberRepository memberRepository, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    @Override
    public Page<GetAllMembersResponse> handle(GetAllMembersQuery query) {
        Pageable pageable = PageRequest.of(query.pageNumber(), query.pageSize());
        Page<Member> members = memberRepository.findAll(pageable);
        return members.map(memberMapper::getAllMembersResponseFromMember);
    }
}
