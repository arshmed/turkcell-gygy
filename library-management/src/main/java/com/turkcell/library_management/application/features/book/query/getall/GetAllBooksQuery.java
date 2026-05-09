package com.turkcell.library_management.application.features.book.query.getall;

import org.springframework.data.domain.Page;

import com.turkcell.library_management.core.mediator.cqrs.Query;

public record GetAllBooksQuery(int pageNumber, int pageSize) implements Query<Page<GetAllBooksResponse>> {}
