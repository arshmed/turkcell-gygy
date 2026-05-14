package com.turkcell.spring_cqrs.application.features.category.query.getall;

import java.util.List;

import org.springframework.data.domain.Page;

import com.turkcell.spring_cqrs.core.mediator.cqrs.Query;
import com.turkcell.spring_cqrs.core.security.authorization.AuthorizedRequest;

public record GetAllCategoriesQuery(int pageNumber, int pageSize) implements Query<Page<GetAllCategoriesResponse>>, AuthorizedRequest {
    @Override
    public List<String> getRequiredRoles() {
        return List.of();
    }
}