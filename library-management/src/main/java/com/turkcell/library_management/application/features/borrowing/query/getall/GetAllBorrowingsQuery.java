package com.turkcell.library_management.application.features.borrowing.query.getall;

import org.springframework.data.domain.Page;

import com.turkcell.library_management.core.mediator.cqrs.Query;

public record GetAllBorrowingsQuery(int pageNumber, int pageSize) implements Query<Page<GetAllBorrowingsResponse>> {}
