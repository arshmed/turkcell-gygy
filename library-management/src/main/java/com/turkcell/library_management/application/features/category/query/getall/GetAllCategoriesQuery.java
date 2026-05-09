package com.turkcell.library_management.application.features.category.query.getall;

import org.springframework.data.domain.Page;

import com.turkcell.library_management.core.mediator.cqrs.Query;

public record GetAllCategoriesQuery(int pageNumber, int pageSize) implements Query<Page<GetAllCategoriesResponse>> {}
