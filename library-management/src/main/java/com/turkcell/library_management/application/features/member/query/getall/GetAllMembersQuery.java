package com.turkcell.library_management.application.features.member.query.getall;

import org.springframework.data.domain.Page;

import com.turkcell.library_management.core.mediator.cqrs.Query;

public record GetAllMembersQuery(int pageNumber, int pageSize) implements Query<Page<GetAllMembersResponse>> {}
